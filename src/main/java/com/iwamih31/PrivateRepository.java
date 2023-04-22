package com.iwamih31;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivateRepository extends JpaRepository<Private, Integer> {

		/**	Private情報取得 */
		@Query("select private"
				+ " from Private private"
				+ " where room = :room"
				+ " and name = :name"
				+ " and date = :date")
		public List<Private> getPrivate(
				@Param("room")Integer room,
				@Param("name")String name,
				@Param("date")String date
			);

		/**	Private情報更新	*/
		@Modifying
		@Query("update Private"
				 + " set"
				 + "  time = :time,"
				 + "  sleep = :sleep"
				 + " where"
				 + "  id = :id")
		public Integer updateUser(
				@Param("id") int id,
				@Param("time") String time,
				@Param("sleep") String sleep
			);
	};

