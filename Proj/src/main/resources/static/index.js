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

function renderNode(node) {
    const li = document.createElement('li');
    if (node.type === 'category') {
        const span = document.createElement('span');
        span.className = 'cat';
        span.textContent = node.title + ' ';
        const btn = document.createElement('button');
        btn.textContent = 'Sumar';
        btn.onclick = () => loadSummary('/nodes/' + node.id + '/summary', node.title);
        li.append(span, btn, ' ', deleteButton(node));
        const ul = document.createElement('ul');
        node.children.forEach(child => ul.appendChild(renderNode(child)));
        li.appendChild(ul);
    } else {
        const today = new Date().toISOString().slice(0, 10);
        const overdue = !node.done && node.deadline && node.deadline < today;

        const badge = document.createElement('span');
        badge.className = 'badge ' + (node.done ? 'badge-done' : overdue ? 'badge-overdue' : 'badge-open');
        badge.textContent = node.done ? 'Gata' : overdue ? 'Întârziată' : 'În lucru';

        const title = document.createElement('span');
        title.textContent = node.title + ' (' + node.estimatedMinutes + ' min, termen ' + (node.deadline || '-') + ')';
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

async function loadTree() {
    try {
        const roots = await api('/nodes');
        const tree = document.getElementById('tree');
        tree.replaceChildren();
        const ul = document.createElement('ul');
        roots.forEach(root => ul.appendChild(renderNode(root)));
        tree.appendChild(ul);

        const select = document.getElementById('parent');
        select.replaceChildren(new Option('- nivel superior -', ''));
        fillParents(roots, 0, select);
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

document.getElementById('form').onsubmit = async e => {
    e.preventDefault();
    const type = document.getElementById('type').value;
    const parent = document.getElementById('parent').value;
    const body = { title: document.getElementById('title').value, parentId: parent ? Number(parent) : null };
    if (type === 'task') {
        body.estimatedMinutes = Number(document.getElementById('minutes').value);
        body.deadline = document.getElementById('deadline').value || null;
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