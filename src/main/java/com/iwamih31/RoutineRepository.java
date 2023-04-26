package com.iwamih31;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutineRepository extends JpaRepository<Routine, Integer> {

	/**	Action情報取得 */
	@Query("select routine"
			+ " from Routine routine"
			+ " where date = :date"
			+ " order by action.time desc")
	List<Routine> routineList(String date);
}
