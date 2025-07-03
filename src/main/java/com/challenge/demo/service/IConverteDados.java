package com.challenge.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public interface IConverteDados {

    <T>T obterDados(String json, Class<T> classe);

    // Se você quiser que o método objeto(String json) faça parte do contrato
    JsonNode objeto(String json) throws JsonProcessingException;
}
