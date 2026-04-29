package com.runaitec.credimacpato.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MeasureUnitType {
    NIU("NIU", "Unidad", "Bienes contados por unidad (ej. ropa, electrodomésticos).", false),
    KGM("KGM", "Kilogramo", "Productos vendidos por peso (ej. alimentos, metales).", true),
    LTR("LTR", "Litro", "Líquidos (ej. combustibles, bebidas).", true),
    MTR("MTR", "Metro", "Bienes vendidos por longitud (ej. cables, telas).", true),
    MTK("MTK", "Metro cuadrado", "Bienes vendidos por superficie (ej. pisos, alfombras).", true),
    MTQ("MTQ", "Metro cúbico", "Bienes vendidos por volumen (ej. arena, concreto).", true),
    CS("CS", "Caja", "Productos empaquetados en cajas.", false),
    BX("BX", "Bulto / Paquete", "Bienes agrupados en bultos.", false),
    PR("PR", "Par", "Bienes vendidos en pares (ej. zapatos).", false),
    ZZ("ZZ", "Servicio", "Prestación de servicios (ej. consultoría, transporte).", false);

    private final String unitCode;
    private final String description;
    private final String typicalUse;
    private final boolean isDecimal;
}