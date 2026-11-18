package ro.uvt.info.proj.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.uvt.info.proj.Node;

import java.util.List;

public interface NodeRepository extends JpaRepository<Node, Integer> {
    List<Node> findByParentIsNull();
}