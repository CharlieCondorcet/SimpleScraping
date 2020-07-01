/*
 * Copyright (c) 2020. Charlie Condorcet Engineering Student
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0 which is available at
 *  http://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
 *  which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 *  SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
 */

package cl.ucn.disc.pdis.simplescraper.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * The Functionary model class.
 *
 * @author Charlie Condorcet.
 */
@DatabaseTable(tableName = "functionary")
public final class Functionary {

    /**
     * The id: Primary Key.
     */
    @DatabaseField(generatedId = true)
    private int id;

    /**
     * The Nombre.
     */
    @DatabaseField(canBeNull = false)
    private String nombre;

    /**
     * The Cargo.
     */
    @DatabaseField(canBeNull = true)
    private String cargo;

    /**
     * The Unidad.
     */
    @DatabaseField(canBeNull = true)
    private String unidad;

    /**
     * The E-Mail.
     */
    @DatabaseField(canBeNull = true)
    private String email;

    /**
     * The Telefono.
     */
    @DatabaseField(canBeNull = true)
    private String telefono;

    /**
     * The Oficina.
     */
    @DatabaseField(canBeNull = true)
    private String oficina;

    /**
     * The Direccion.
     */
    @DatabaseField(canBeNull = true)
    private String direccion;

    /**
     * Empty contructor; Default visivility + empty body.
     */
    public Functionary() {
        // nothing here.
    }

    /**
     * Principal Constructor.
     *
     * @param nombre
     * @param cargo
     * @param unidad
     * @param email
     * @param telefono
     * @param oficina
     * @param direccion
     */
    public Functionary(String nombre, String cargo, String unidad, String email, String telefono, String oficina, String direccion) {
        this.nombre = nombre;
        this.cargo = cargo;
        this.unidad = unidad;
        this.email = email;
        this.telefono = telefono;
        this.oficina = oficina;
        this.direccion = direccion;
    }

    /**
     * Getter to ID.
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter to Nombre.
     *
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Getter to Cargo.
     *
     * @return cargo
     */
    public String getCargo() {
        return cargo;
    }

    /**
     * Getter to Unidad.
     *
     * @return unidad
     */
    public String getUnidad() {
        return unidad;
    }

    /**
     * Getter to E-mail.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter to Telefono.
     *
     * @return telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Getter to Oficina.
     *
     * @return oficina
     */
    public String getOficina() {
        return oficina;
    }

    /**
     * Getter to Direccion.
     *
     * @return direccion
     */
    public String getDireccion() {
        return direccion;
    }

}
