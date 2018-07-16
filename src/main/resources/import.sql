insert into grupo (id, nome, hora_ultima_alteracao, editavel, status) values (1, 'Padrão', sysdate(), 0, 'VIGENTE');

insert into atividade (id, descricao, conteudo, grupo_id, status) values (1, 'Consulta médica', 'Ir ao otorrinolaringologista', 1, 'VIGENTE');
