package br.com.alura.literalura.service;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverteDados implements IConverteDados {

    private final ObjectMapper mapper;

    public ConverteDados() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public <T> T obeterDados(String json, Class<T> tipoClasse) {
        try {
            return mapper.readValue(json, tipoClasse);
        } catch (Exception e) {
            System.out.println("Erro ao converter JSON: " + e.getMessage());
            return null;
        }
    }
}
