async function api(path, options) {
    const res = await fetch(path, options);
    if (!res.ok) throw new Error('Eroare ' + res.status);
    if (res.status === 204) return null;
    return res.json();
}

function showError(e) { document.getElementById('msg').textContent = e.message; }
async function setDone(id, done) {
    try {
        await api('/tasks/' + id + '/done', {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ done: done })
        });
        await loadTree();
    } catch (e) { showError(e); }
}
async function deleteNode(node) {
    const extra = node.type === 'category' ? ' și tot ce conține' : '';
    if (!confirm('Ștergi „' + node.title + '"' + extra + '?')) return;
    try {
        await api('/nodes/' + node.id, { method: 'DELETE' });
        await loadTree();
    } catch (e) { showError(e); }
}

function deleteButton(node) {
    const btn = document.createElement('button');
    btn.textContent = 'Delete';
    btn.className = 'danger';
    btn.onclick = () => deleteNode(node);
    return btn;
}

// --- stare expand/collapse (păstrată în browser, supraviețuiește reîncărcării) ---
const COLLAPSED_KEY = 'collapsedCategories';
let lastRoots = [];

function loadCollapsed() {
    try { return new Set(JSON.parse(localStorage.getItem(COLLAPSED_KEY) || '[]')); }
    catch (e) { return new Set(); }
}
function saveCollapsed(set) {
    try { localStorage.setItem(COLLAPSED_KEY, JSON.stringify([...set])); } catch (e) { /* ignorăm */ }
}
function collectCategoryIds(nodes, out) {
    nodes.forEach(n => {
        if (n.type !== 'category') return;
        out.push(n.id);
        collectCategoryIds(n.children, out);
    });
    return out;
}
function setAllCollapsed(collapsed) {
    saveCollapsed(collapsed ? new Set(collectCategoryIds(lastRoots, [])) : new Set());
    renderTree();
}

// Nefăcutele (și categoriile) primele, sarcinile făcute la final; ordinea relativă rămâne neschimbată.
function sortChildren(children) {
    const isDoneTask = n => n.type === 'task' && n.done ? 1 : 0;
    return children
        .map((node, index) => ({ node, index }))
        .sort((a, b) => isDoneTask(a.node) - isDoneTask(b.node) || a.index - b.index)
        .map(x => x.node);
}

function describeTask(node) {
    const parts = [];
    if (node.estimatedMinutes != null) parts.push(node.estimatedMinutes + ' min');
    if (node.deadline) parts.push('termen ' + node.deadline);
    return node.title + (parts.length ? ' (' + parts.join(', ') + ')' : '');
}

function renderNode(node) {
    const li = document.createElement('li');
    if (node.type === 'category') {
        const collapsed = loadCollapsed();
        const isCollapsed = collapsed.has(node.id);

        const toggle = document.createElement('button');
        toggle.className = 'toggle';
        toggle.textContent = isCollapsed ? '▸' : '▾';
        toggle.title = isCollapsed ? 'Extinde' : 'Restrânge';
        toggle.onclick = () => {
            const set = loadCollapsed();
            if (set.has(node.id)) set.delete(node.id); else set.add(node.id);
            saveCollapsed(set);
            renderTree();
        };

        const span = document.createElement('span');
        span.className = 'cat';
        span.textContent = node.title + ' ';
        const count = document.createElement('span');
        count.className = 'count';
        const tasks = node.children.filter(c => c.type === 'task');
        count.textContent = '(' + tasks.filter(t => t.done).length + '/' + tasks.length + ')';
        const btn = document.createElement('button');
        btn.textContent = 'Sumar';
        btn.onclick = () => loadSummary('/nodes/' + node.id + '/summary', node.title);
        li.append(toggle, ' ', span, count, btn, ' ', deleteButton(node));
        if (!isCollapsed) {
            const ul = document.createElement('ul');
            sortChildren(node.children).forEach(child => ul.appendChild(renderNode(child)));
            li.appendChild(ul);
        }
    } else {
        const today = new Date().toISOString().slice(0, 10);
        const overdue = !node.done && node.deadline && node.deadline < today;

        const badge = document.createElement('span');
        badge.className = 'badge ' + (node.done ? 'badge-done' : overdue ? 'badge-overdue' : 'badge-open');
        badge.textContent = node.done ? 'Gata' : overdue ? 'Întârziată' : 'În lucru';

        const title = document.createElement('span');
        title.textContent = describeTask(node);
        if (node.done) title.className = 'done';

        const btn = document.createElement('button');
        btn.textContent = node.done ? 'Redeschide' : 'Done';
        btn.onclick = () => setDone(node.id, !node.done);

        li.append(badge, ' ', title, ' ', btn, ' ', deleteButton(node));
    }
    return li;
}
function fillParents(nodes, depth, select) {
    nodes.forEach(node => {
        if (node.type !== 'category') return;
        const opt = document.createElement('option');
        opt.value = node.id;
        opt.textContent = '\u00A0\u00A0'.repeat(depth) + node.title;
        select.appendChild(opt);
        fillParents(node.children, depth + 1, select);
    });
}

function renderTree() {
    const tree = document.getElementById('tree');
    tree.replaceChildren();
    const ul = document.createElement('ul');
    sortChildren(lastRoots).forEach(root => ul.appendChild(renderNode(root)));
    tree.appendChild(ul);
}

async function loadTree() {
    try {
        const roots = await api('/nodes');
        lastRoots = roots;
        renderTree();

        const select = document.getElementById('parent');
        const selected = select.value;
        select.replaceChildren(new Option('- nivel superior -', ''));
        fillParents(roots, 0, select);
        select.value = selected;   // păstrează părintele ales după reîncărcare
        showError({ message: '' });
    } catch (e) { showError(e); }
}

async function loadSummary(path, label) {
    try {
        const s = await api(path);
        document.getElementById('summary').textContent =
            'Sumar: ' + label + '\n' +
            'Categorii: ' + s.categories + '\n' +
            'Sarcini: ' + s.tasks + ' (terminate: ' + s.doneTasks + ')\n' +
            'Minute totale: ' + s.totalMinutes + ', rămase: ' + s.remainingMinutes + '\n' +
            'Cu termen depășit: ' + s.overdueTasks;
    } catch (e) { showError(e); }
}

document.getElementById('type').onchange = e => {
    document.querySelectorAll('.task-only').forEach(el => el.hidden = e.target.value !== 'task');
};

document.getElementById('allSummary').onclick = () => loadSummary('/nodes/summary', 'tot');
document.getElementById('expandAll').onclick = () => setAllCollapsed(false);
document.getElementById('collapseAll').onclick = () => setAllCollapsed(true);

document.getElementById('form').onsubmit = async e => {
    e.preventDefault();
    const type = document.getElementById('type').value;
    const parent = document.getElementById('parent').value;
    const body = { title: document.getElementById('title').value, parentId: parent ? Number(parent) : null };
    if (type === 'task') {
        const minutes = document.getElementById('minutes').value;
        body.estimatedMinutes = minutes === '' ? null : Number(minutes);   // opțional
        body.deadline = document.getElementById('deadline').value || null;  // opțional
    }
    try {
        await api(type === 'task' ? '/tasks' : '/categories', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body)
        });
        document.querySelectorAll('.task-only').forEach(el => el.hidden = true);
        document.getElementById('title').focus();
        await loadTree();
    } catch (err) { showError(err); }
};

loadTree();