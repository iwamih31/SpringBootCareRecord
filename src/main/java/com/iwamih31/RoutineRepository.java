package com.iwamih31;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutineRepository extends JpaRepository<Routine, Integer> {

	/**	routine情報取得 */
	@Query("select routine"
			+ " from Routine routine"
			+ " where routine.date = :date"
			+ " order by routine.time asc")
	List<Routine> routineList(String date);

	/** routine情報取得（日付指定）*/
	@Query("select routine"
			+ " from Routine routine"
			+ " where routine.room = :user_Room"
			+ " and   routine.name = :user_Name"
			+ " and   routine.date = :date"
			+ " order by"
			+ " routine.time asc")
	List<Routine> routine_List(int user_Room, String user_Name, String date);

	/**	routine情報取得（月単位）
	 * @param user_id
	 * @param date_Like */
	@Query("select routine"
			+ " from Routine routine"
			+ " where routine.room = :user_Room"
			+ " and   routine.name = :user_Name"
			+ " and   routine.date like %:date_Like%"
			+ " order by"
			+ " routine.date asc,"
			+ " routine.time asc")
	List<Routine> routine_Report(int user_Room, String user_Name, String date_Like);

	/**	routine情報取得（月単位）全利用者分 */
	@Query("select routine"
			+ " from Routine routine"
			+ " where date like %:date_Like%"
			+ " order by"
			+ " routine.date asc,"
			+ " routine.room asc,"
			+ " routine.name asc,"
			+ " routine.time asc")
	List<Routine> routine_Report_All(String date_Like);
}
