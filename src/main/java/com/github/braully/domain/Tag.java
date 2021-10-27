package com.github.braully.domain;

import com.github.braully.domain.AbstractEntity;
import com.github.braully.util.logutil;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity(name = "Tag")
@Access(AccessType.FIELD)
@Table(name = "tag", schema = "base")
public class Tag extends AbstractEntity implements Serializable {
    // TODO: Melhorar essa solução, muito porca

    @Transient
    protected static Set<Tag> modificadas;
    /*
     * pertinente as funções genericas
     */
    @Transient
    protected boolean selecionada;
    @Transient
    protected int quantidade;

    /*
     * usado somente no cadastro de tags - TODO: mover esse controle para a tela
     */
    @Transient
    protected boolean edita;
    // Objeto apensado, para uso generico
    @Transient
    protected Object objeto;
    @ManyToOne(fetch = FetchType.LAZY)
    protected Tag pai;
    @OneToMany(mappedBy = "pai", fetch = FetchType.EAGER)
    protected Set<Tag> filhos;

    public Tag() {
    }

    public Tag(String descricao) {
        this.descricao = descricao;
    }

    public Tag getPai() {
        return pai;
    }

    public void setPai(Tag pai) {
        this.pai = pai;
    }

    public Set<Tag> getFilhos() {
        return filhos;
    }

    public void setFilhos(Set<Tag> filhos) {
        this.filhos = filhos;
    }

    public void setSelecionada(boolean selecionada) {
        this.selecionada = selecionada;
    }

    public boolean isSelecionada() {
        return selecionada;
    }

    public static Set<Tag> getModificadas() {
        return modificadas;
    }

    public void novaTag() {
        if (this.filhos == null) {
            this.filhos = new HashSet<Tag>();
        }
        Tag t = new Tag("...");
        t.setPai(this);
        t.edita = true;
        this.filhos.add(t);
    }

    public void addFilho(Tag filho) {
        if (filho != null) {
            if (this.filhos == null) {
                this.filhos = new HashSet<Tag>();
            }
            filho.setPai(this);
            filho.edita = false;
            this.filhos.add(filho);
            filho.editaTag();
        }
    }

    public void editaTag() {
        this.edita = !edita;
        if (modificadas == null) {
            modificadas = new HashSet<Tag>();
        }
        modificadas.add(this);
    }

    public void setEdita(boolean edita) {
        this.edita = edita;
    }

    public boolean isEdita() {
        return edita;
    }

    public void setObjeto(Object objeto) {
        this.objeto = objeto;
    }

    public Object getObjeto() {
        return objeto;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public Tag novoFilho() {
        Tag tf = new Tag();
        this.addFilho(tf);
        return tf;
    }

    protected String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricaoFormatada() {
        StringBuilder desc = new StringBuilder();
        try {
            Tag pai = this.getPai();
            if (pai != null) {
                desc.append(pai.getDescricaoFormatada());
                desc.append("/");
            }
        } catch (Exception e) {
            logutil.warn("", e);
        }
        desc.append(descricao);
        return desc.toString();
    }

    public Tag[] getFilhosAsArray() {
        Tag[] ts = null;
        Set<? extends Tag> filhos = this.getFilhos();
        if (filhos != null) {
            ts = filhos.toArray(new Tag[0]);
        }
        return ts;
    }

    @Override
    public String toString() {
        return getDescricao();
    }

    public Tag getRaiz() {
        Tag raiz = this.getPai();
        while (raiz != null && raiz.getPai() != null) {
            raiz = raiz.getPai();
        }
        return raiz;
    }

    public Set<Tag> getTodosDecendentes() {
        Set<Tag> tgs = new HashSet<Tag>();
        Set<? extends Tag> filhos = this.getFilhos();
        tgs.addAll(filhos);
        if (filhos != null) {
            for (Tag t : filhos) {
                tgs.addAll(t.getTodosDecendentes());
            }
        }
        return tgs;
    }

    public int compareTo(Tag arg0) {
        return this.descricao.compareTo(arg0.descricao);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!getClass().isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Tag other = (Tag) obj;
        if ((this.descricao == null) ? (other.descricao != null) : !this.descricao.equals(other.descricao)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + (this.descricao != null ? this.descricao.hashCode() : 0);
        return hash;
    }

    public boolean isNovo() {
        return !isPersisted();
    }
}
