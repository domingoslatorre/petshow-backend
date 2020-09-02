# language: pt
Funcionalidade: Animal de estimacao
    Delineacao do Cenario: Operacao bem sucedido
        Dado que acesso <endereco><variavel> adequadamente com metodo <metodo> e corpo <corpo>
        Entao devo receber como retorno um status <statusHttp>
        E um corpo preenchido
        Exemplos:
            | endereco              | variavel | metodo  | statusHttp | corpo                                 |
            | '/animalEstimacao/'   | ''       |'POST'   | 201        | 'AnimalEstimacaoRepresentation'       |
            | '/animalEstimacao/'   | ''       |'GET'    | 200        | ''                                    |
            | '/animalEstimacao/'   | '1'      |'GET'    | 200        | ''                                    |
            | '/animalEstimacao/'   | '1'      |'PUT'    | 200        | 'AnimalEstimacaoRepresentation'       |
            | '/animalEstimacao/'   | '1'      |'DELETE' | 200        | ''                                    |