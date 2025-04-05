package ru.spbu.lenakosteva.feign.mapper;

import org.mapstruct.Mapper;
import ru.spbu.lenakosteva.domain.model.OpenQuestionCard;
import ru.spbu.lenakosteva.feign.dto.OpenQuestionCardDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapperDto {

    OpenQuestionCard toModel(OpenQuestionCardDto dto);
    OpenQuestionCardDto toDto(OpenQuestionCard model);
    List<OpenQuestionCard> toModel(List<OpenQuestionCardDto> dto);
    List<OpenQuestionCardDto> toDto(List<OpenQuestionCard> model);
}
