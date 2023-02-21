package com.project.trashure.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Email {

    String emisor;
    String destinatario;
    String asunto;

    String idObjeto;

    String texto;

}
