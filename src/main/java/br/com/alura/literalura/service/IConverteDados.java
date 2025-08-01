package br.com.alura.literalura.service;

public interface IConverteDados {
    <T> T obeterDados(String json, Class<T> tipoClasse);
}
