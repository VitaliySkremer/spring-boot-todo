package ru.skremer.todoapplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skremer.todoapplication.entity.Record;
import ru.skremer.todoapplication.entity.RecordStatus;
import ru.skremer.todoapplication.entity.User;
import ru.skremer.todoapplication.entity.dto.RecordsContainerDto;
import ru.skremer.todoapplication.repository.RecordRepository;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RecordService {
    private final RecordRepository recordRepository;
    private final UserService userService;

    @Transactional(readOnly = true)
    public RecordsContainerDto findAll(String filter) {
        User currentUser = userService.getCurrentUser();

        List<Record> recordList = currentUser.getRecords()
                .stream()
                .sorted(Comparator.comparingInt(Record::getId))
                .toList();

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
            return new RecordsContainerDto(recordList, numberOfDoneRecords, numberOfActiveRecords, currentUser);

        String filterInUpperCasse = filter.toUpperCase();
        List<String> allowedFilter = Arrays.stream(RecordStatus.values()).map(Enum::name).toList();

        if (!allowedFilter.contains(filterInUpperCasse))
            return new RecordsContainerDto(recordList, numberOfDoneRecords, numberOfActiveRecords, currentUser);

        List<Record> filteredRecordList = recordList.stream().filter(item -> item.getStatus() == RecordStatus.valueOf(filterInUpperCasse)).collect(Collectors.toList());

        return new RecordsContainerDto(filteredRecordList, numberOfDoneRecords, numberOfActiveRecords, currentUser);
    }

    public void addRecord(String title) {
        String titleNormalized = title.trim();

        if (titleNormalized.isEmpty()) return;

        User currentUser = userService.getCurrentUser();

        recordRepository.save(new Record(titleNormalized, currentUser));
    }

    public void makeRecordDone(int id) {
        recordRepository.updateStatusById(id, RecordStatus.DONE);
    }

    public void deleteRecord(int id) {
        recordRepository.deleteById(id);
    }
}
