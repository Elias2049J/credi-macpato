package com.runaitec.credimacpato.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MeasureUnitType {
    NIU("NIU", "Unidad", "Bienes contados por unidad (ej. ropa, electrodomésticos)."),
    KGM("KGM", "Kilogramo", "Productos vendidos por peso (ej. alimentos, metales)."),
    LTR("LTR", "Litro", "Líquidos (ej. combustibles, bebidas)."),
    MTR("MTR", "Metro", "Bienes vendidos por longitud (ej. cables, telas)."),
    MTK("MTK", "Metro cuadrado", "Bienes vendidos por superficie (ej. pisos, alfombras)."),
    MTQ("MTQ", "Metro cúbico", "Bienes vendidos por volumen (ej. arena, concreto)."),
    CS("CS", "Caja", "Productos empaquetados en cajas."),
    BX("BX", "Bulto / Paquete", "Bienes agrupados en bultos."),
    PR("PR", "Par", "Bienes vendidos en pares (ej. zapatos)."),
    ZZ("ZZ", "Servicio", "Prestación de servicios (ej. consultoría, transporte).");

    private final String unitCode;
    private final String description;
    private final String typicalUse;
}