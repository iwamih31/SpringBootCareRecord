package com.iwamih31;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRepository extends JpaRepository<Action, Integer> {

		/**	Action情報取得（日付指定） */
		@Query("select action"
				+ " from Action action"
				+ " where user_id = :user_id"
				+ " and date = :date"
				+ " order by action.time asc")
		public List<Action> getActions(
				@Param("user_id")Integer user_id,
				@Param("date")String date
			);

		/**	Actionリスト取得（年月指定）	*/
		@Query("select action"
				+ " from Action action"
				+ " where action.user_id = :user_id"
				+ " and action.date like %:date_Like%"
				+ " order by action.date asc"
				+ " , action.time asc")
		public List<Action> action_List(
				@Param("user_id") Integer user_id,
				@Param("date_Like") String date_Like
			);

		/**	Actionリスト取得（全ユーザー年月指定）	*/
		@Query("select action"
				+ " from Action action"
				+ " where action.date like %:date_Like%"
				+ " order by action.date asc"
				+ " , action.time asc")
		public List<Action> action_List_All(
				@Param("date_Like") String date_Like
				);

	}

