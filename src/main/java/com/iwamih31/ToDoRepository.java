package com.iwamih31;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Integer> {

	/**	action情報取得 */
	@Query("select todo.action"
			+ " from ToDo todo")
	String[] actions();

	/**	Todo情報（時間昇順）取得 */
	@Query("select todo"
			+ " from ToDo todo"
			+ " order by todo.time asc")
	List<ToDo> todoList();
}
