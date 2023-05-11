package com.ceiba.biblioteca.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;


@Entity
@Table(name = "prestamos")
public class PrestamoModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;

    @Column(name = "isbn", length = 10)
    private String isbn;

    @Column(name = "identificacion_usuario", length = 10)
    private String identificacionUsuario;

    private int tipoUsuario;

    private Date fechaMaximaDevolucion;


    public int getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getIdentificacionUsuario() {
        return identificacionUsuario;
    }
    
    public void setIdentificacionUsuario(String identificacionUsuario) {
        this.identificacionUsuario = identificacionUsuario;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getFechaMaximaDevolucion() {
        String pattern = "dd/MM/yyyy";
        DateFormat dateHelper = new SimpleDateFormat(pattern);
        
        return dateHelper.format(this.fechaMaximaDevolucion);
    }

    @PrePersist
    public void setFechaMaximaDevolucion() {
        Date today = new Date();
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.setTime(today);

        switch(this.tipoUsuario){
            case (1) : {
                Date maxDate = getMaxDate(todayCalendar, 10);
                
                this.fechaMaximaDevolucion = maxDate;
                break;
            }
            case (2) : {
                Date maxDate = getMaxDate(todayCalendar, 8);

                
                this.fechaMaximaDevolucion = maxDate;
                break;
            }
            case (3) : {
                Date maxDate = getMaxDate(todayCalendar, 7);
                
                this.fechaMaximaDevolucion = maxDate;
                break;
            }
            default: 
                break;
        }
    }

    private Date getMaxDate(Calendar todayCalendar, int ndays){
        int sumDays = 0;
        while (sumDays < ndays) {
            int dayOfWeek = todayCalendar.get(Calendar.DAY_OF_WEEK);
            todayCalendar.add(Calendar.DAY_OF_MONTH, 1);
            if(dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY){
                sumDays++;
            }
        }

        return todayCalendar.getTime();
    } 

}
