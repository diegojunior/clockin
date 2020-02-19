package br.com.diegjun.clockin.mapper;


import br.com.diegjun.clockin.model.Model;

public interface JsonMapper<J extends Json, M extends Model> {

    public M convertJsonToModel(J json);

    public J convertModelToJson(M model);
}
