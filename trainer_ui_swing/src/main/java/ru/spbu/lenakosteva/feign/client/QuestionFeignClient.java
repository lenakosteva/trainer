package ru.spbu.lenakosteva.feign.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import ru.spbu.lenakosteva.feign.dto.OpenQuestionCardDto;

import java.util.List;

public interface QuestionFeignClient {

    @RequestLine("GET api/v1/question/{id}")
    @Headers({"Content-Type: application/json"})
    OpenQuestionCardDto card(@Param("id") Long id);

    @RequestLine("GET api/v1/question")
    @Headers({"Content-Type: application/json"})
    List<OpenQuestionCardDto> list();

    @RequestLine("POST api/v1/question")
    @Headers({"Content-Type: application/json"})
    void add(OpenQuestionCardDto questionCard);

    @RequestLine("PUT api/v1/question")
    @Headers({"Content-Type: application/json"})
    void update(OpenQuestionCardDto questionCard);

    @RequestLine("DELETE api/v1/question/{id}")
    @Headers({"Content-Type: application/json"})
    void remove(@Param("id") Long id);
}
