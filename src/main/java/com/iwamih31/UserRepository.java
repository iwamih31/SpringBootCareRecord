package com.iwamih31;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	/**	Private情報取得 */
	@Query("select user"
			+ " from User user"
			+ " where use = '利用中'"
			+ " order by user.room asc")
	public List<User> userList();

}
