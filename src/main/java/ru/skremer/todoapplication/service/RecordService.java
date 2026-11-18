package ru.skremer.todoapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skremer.todoapplication.entity.Record;
import ru.skremer.todoapplication.entity.RecordStatus;
import ru.skremer.todoapplication.entity.dto.RecordsContainerDto;
import ru.skremer.todoapplication.repository.RecordRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecordService {
    private final RecordRepository recordRepository;

    @Autowired
    public RecordService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @Transactional(readOnly = true)
    public RecordsContainerDto findAll(String filter) {
        List<Record> recordList = recordRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));

        int numberOfDoneRecords = 0;
        int numberOfActiveRecords = 0;

        for (Record record : recordList) {
            if (record.getStatus().equals(RecordStatus.DONE)) {
                numberOfDoneRecords++;
            } else {
                numberOfActiveRecords++;
            }
        }

        if (filter == null || filter.isBlank())
            return new RecordsContainerDto(recordList, numberOfDoneRecords, numberOfActiveRecords);

        String filterInUpperCasse = filter.toUpperCase();
        List<String> allowedFilter = Arrays
                .stream(RecordStatus.values())
                .map(Enum::name)
                .toList();

        if (!allowedFilter.contains(filterInUpperCasse))
            return new RecordsContainerDto(recordList, numberOfDoneRecords, numberOfActiveRecords);

        List<Record> filteredRecordList = recordList
                .stream()
                .filter(item -> item.getStatus() == RecordStatus.valueOf(filterInUpperCasse))
                .collect(Collectors.toList());

        return new RecordsContainerDto(filteredRecordList, numberOfDoneRecords, numberOfActiveRecords);
    }

    public void addRecord(String title) {
        String titleNormalized = title.trim();

        if (titleNormalized.isEmpty()) return;

        recordRepository.save(new Record(titleNormalized));
    }

    public void makeRecordDone(int id) {
        recordRepository.updateStatusById(id, RecordStatus.DONE);
    }

    public void deleteRecord(int id) {
        recordRepository.deleteById(id);
    }
}
