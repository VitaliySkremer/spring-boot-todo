package ru.skremer.todoapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skremer.todoapplication.entity.Record;
import ru.skremer.todoapplication.entity.RecordStatus;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer> {
    @Modifying
    @Query("UPDATE Record r set r.status = :status WHERE r.id = :id")
    void updateStatusById(@Param("id") int id, @Param("status") RecordStatus status);

    List<Record> findAllByStatus(RecordStatus status);
    List<Record> findAllByStatusAndTitleContainsOrderByIdDesc(RecordStatus status, String title);
    List<Record> findAllByTitleContains(String title);
    int countAllByStatus(RecordStatus status);
}
