package com.iwamih31;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CareRecordRepository extends JpaRepository<CareRecord, Integer> {

	/**	Private情報取得 */
	@Query("select careRecord"
			+ " from CareRecord careRecord"
			+ " where use = '利用中'")
	public List<CareRecord> userList();
}
