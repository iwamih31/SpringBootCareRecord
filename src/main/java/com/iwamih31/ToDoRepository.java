package com.iwamih31;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Integer> {

	/**	todo情報取得 */
	@Query("select todo.action"
			+ " from ToDo todo")
	String[] todoNames();
}
