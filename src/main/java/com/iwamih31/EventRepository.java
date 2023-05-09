package com.iwamih31;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

	/**	イベント情報取得(日時昇順) */
	@Query("select event"
			+ " from Event event"
			+ " order by event.datetime asc")
	public List<Event> eventList();

	/**	イベント情報取得(localDateTime以降のみ 日時昇順) */
	@Query("select event"
			+ " from Event event"
			+ " where datetime > :localDateTime"
			+ " order by event.datetime asc")
	public List<Event> eventList(LocalDateTime localDateTime);

}
