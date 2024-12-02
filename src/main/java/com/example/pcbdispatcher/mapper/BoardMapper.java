package com.example.pcbdispatcher.mapper;

import com.example.pcbdispatcher.dto.BoardDto;
import com.example.pcbdispatcher.entity.Board;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BoardMapper {

    private final ModelMapper modelMapper;

    public BoardDto toDto(Board board) {
        return modelMapper.map(board, BoardDto.class);
    }

    public List<BoardDto> toBoardDtoList(List<Board> boards) {
        return boards.stream()
                .map(board -> modelMapper.map(board, BoardDto.class))
                .toList();
    }
}
