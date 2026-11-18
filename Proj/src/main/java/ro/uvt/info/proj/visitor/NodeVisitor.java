package ro.uvt.info.proj.visitor;

import ro.uvt.info.proj.Category;
import ro.uvt.info.proj.Task;

public interface NodeVisitor {
    void visit(Category category);
    void visit(Task task);
}