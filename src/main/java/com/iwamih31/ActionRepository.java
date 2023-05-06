package com.iwamih31;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRepository extends JpaRepository<Action, Integer> {

		/**	Action情報取得 */
		@Query("select action"
				+ " from Action action"
				+ " where user_id = :user_id"
				+ " and date = :date"
				+ " order by action.time asc")
		public List<Action> getActions(
				@Param("user_id")Integer user_id,
				@Param("date")String date
			);

		/**	Action情報更新	*/
		@Modifying
		@Query("update Action"
				 + " set"
				 + "  time = :time,"
				 + "  sleep = :sleep"
				 + " where"
				 + "  id = :id")
		public Integer updateActio(
				@Param("id") int id,
				@Param("time") String time,
				@Param("sleep") String sleep
			);
	};

