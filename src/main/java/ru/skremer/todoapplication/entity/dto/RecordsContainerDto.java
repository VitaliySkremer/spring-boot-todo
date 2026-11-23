package ru.skremer.todoapplication.entity.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.skremer.todoapplication.entity.Record;
import ru.skremer.todoapplication.entity.User;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class RecordsContainerDto {
    private final List<Record> records;
    private final int numberOfDoneRecords;
    private final int numberOfActiveRecords;
    private final User user;
}
