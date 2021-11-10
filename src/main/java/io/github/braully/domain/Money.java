/* MIT License
* 
* Copyright (c) 2021 Braully Rocha
* 
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
* 
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/

package io.github.braully.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Currency;
import java.util.Locale;
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.MonetaryAmountFactory;
import javax.money.MonetaryContext;
import javax.money.NumberValue;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
@Access(AccessType.FIELD)
public class Money implements Serializable, Comparable<MonetaryAmount>, MonetaryAmount {

    public static final long FATOR_UNIDADE = 100;
    /**
     *
     */
    public static final Locale REGIAO = new Locale("pt", "BR");
    public static final DecimalFormat DEFAULT_FORMAT = new DecimalFormat("###,###,##0.00");
    public static final CurrencyUnit DEFAULT_CURRENCY = Monetary.getCurrency("BRL");

    /*
     * 
     */
    @Column(name = "valor")
    protected long value;
    @Transient
    protected MonetaryAmount delegate;

    public Money() {
        super();
    }

    final void valor(Long valor) {
        this.value = valor;
        delegate = org.javamoney.moneta.Money.of(valor, DEFAULT_CURRENCY);
    }

    public Money(BigDecimal valorBig) {
        this();
        if (valorBig != null) {
            valor(valorBig.unscaledValue().longValue());
        }
    }

    public Money(Double d) {
        this();
        if (d != null) {
            int tmp = (int) (d * FATOR_UNIDADE);
            valor((long) tmp);
        }
    }

    public Money(Long valor) {
        this();
        if (valor != null) {
            valor(valor);
        }
    }

    public Money(long valor) {
        this();
        this.valor(valor);
    }

    public Money(long valor, Currency moeda) {
        this(moeda, valor);
    }

    public Money(Currency moeda, long valor) {
        this();
        this.valor(valor);
    }

    public Money(String arg2) {
        this();
        this.parser(arg2);
    }

    public long getValor() {
        return value;
    }

    public void setValor(long valor) {
        this.value = valor;
    }

    public Currency getMoeda() {
        //return ConfiguracoesRegionais.MOEDA;
        return Currency.getInstance("BRL");
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (value ^ (value >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!getClass().isAssignableFrom(obj.getClass())) {
            return false;
        }
        Money other = (Money) obj;
        if (value != other.value) {
            return false;
        }
        return true;
    }

    public Money adiciona(Money preco) {
        if (preco == null) {
            return this;
        }
        return new Money(preco.value + this.value);
    }

    public Money adiciona(Number big) {
        if (big == null) {
            return this;
        }
        return new Money(big.longValue() + this.value);
    }

    public Money subtrai(Money preco) {
        Money res = this;
        if (preco != null) {
            res = new Money(this.value - preco.value);
        }
        return res;
    }

    public String getDescricaoFormatada() {
        return formatar();
    }

    public String toString() {
        return this.formatar();
    }

    public String formatar() {
        StringBuilder sb = new StringBuilder();
        if (this.getMoeda() != null) {
            sb.append(this.getMoeda().getSymbol());
            sb.append(" ");
        }
        sb.append(formatarValor());
        return sb.toString();
    }

    public String formatarValor() {
//        StringBuilder sb = new StringBuilder();
//        long unidade = this.getUnidade();
//        sb.append(unidade);
//        sb.append(",");
//        long centavos = this.getSubUnidade();
//        if (centavos >= 0 && centavos < 10) {
//            sb.append(0);
//        }
//        if (centavos < 0 && unidade != 0) {
//            centavos = Math.abs(centavos);
//        }
//        sb.append(centavos);
//        return sb.toString();
        return formatar(value);
    }

    //TODO: Alterar todos formatadores dessa classe para esse metodo
    public static String formatar(long valor) {
        double val = ((double) valor / FATOR_UNIDADE);
        return DEFAULT_FORMAT.format(val);
    }

    public static String formatar(BigDecimal total) {
        if (total == null) {
            return "";
        }
        return formatar(total.unscaledValue().longValue());
    }

    public static final Number parserNumber(String valor) throws ParseException {
        return DEFAULT_FORMAT.parse(valor);
    }

    public final void parser(String arg2) {
        this.valor(this.parseValorFormatado(arg2));
    }

    public long getUnidade() {
        return this.value / FATOR_UNIDADE;
    }

    public long getSubUnidade() {
        return this.value % FATOR_UNIDADE;
    }

    public Money multiplica(long escalar) {
        Money res = this;
        res = new Money(this.value * escalar);
        return res;
    }

    public Money porcentagem(Number l) {
        return this.porcentagem(l.doubleValue());
    }

    public Money porcentagemFator(Long l) {
        return this.porcentagem(((double) l / FATOR_UNIDADE));
    }

    public Money porcentagemFator(long l) {
        return this.porcentagem(((double) l / FATOR_UNIDADE));
    }

    public Money porcentagemFator(String val) {
        return this.porcentagem(val);
    }

    public Money porcentagem(String val) {
        if (val != null && val.contains(",")) {
            val = val.replaceAll(",", ".");
        }
        return this.porcentagem(Double.parseDouble(val));
    }

    public Money porcentagem(long l) {
        return this.porcentagem((double) l);
    }

    public Money porcentagem(double descontoPontualidade) {
        Money res = this;
        double fatorDesconto = ((double) descontoPontualidade / (double) 100);
        long newVal = Math.round((double) this.value * fatorDesconto);
        res = new Money(newVal);
        return res;
    }

    @Override
    public int compareTo(MonetaryAmount t) {
        int ret = 0;
        if (t != null) {
            ret = (int) (this.value - ((Money) t).value);
        }
        return ret;
    }

    public Double getValorDouble() {
        return this.getValorDouble(value);
    }

    public Double getValorDouble(long val) {
        return ((double) val / FATOR_UNIDADE);
    }

    protected long parseValorFormatado(String arg2) {
        String valorFormatado = arg2;
        String unidade = "0";
        String fracao = "0";
        long valor;

        int parcial = valorFormatado.indexOf(" ");
        if (parcial > 0 && parcial < valorFormatado.length()) {
            valorFormatado = valorFormatado.substring(parcial + 1);
        }

        parcial = valorFormatado.indexOf(",");
        if (parcial < 0) {
            parcial = valorFormatado.indexOf(".");
        }
        if (parcial > 0) {
            unidade = valorFormatado.substring(0, parcial);
            fracao = valorFormatado.substring(parcial + 1, valorFormatado.length());
        } else {
            unidade = unidade + valorFormatado;
        }
        if (fracao.length() <= 1) {
            fracao = fracao + 0;
        } else if (fracao.length() > 2) {
            fracao = fracao.substring(0, 2);
        }
        unidade = unidade.replaceAll("\\D", "");
        valor = Long.parseLong(unidade) * FATOR_UNIDADE;
        valor += Long.parseLong(fracao);
        return valor;
    }

    public Money subtraiValorAbsoluto(Number number) {
        if (number != null) {
            long value = number.longValue();
            if (number instanceof BigDecimal) {
                value = ((BigDecimal) number).unscaledValue().longValue();
            }
            return new Money(this.value - Math.abs(value));
        }
        return this;
    }

    public Money subtraiValorAbsoluto(Money debito) {
        if (debito != null && debito.value != 0) {
            return new Money(this.value - Math.abs(debito.value));
        }
        return this;
    }

    public Money adicionaValorAbsoluto(Number number) {
        if (number != null) {
            long value = number.longValue();
            if (number instanceof BigDecimal) {
                value = ((BigDecimal) number).unscaledValue().longValue();
            }
            return new Money(this.value + Math.abs(value));
        }
        return this;
    }

    public Money adicionaValorAbsoluto(Money credito) {
        if (credito != null && credito.value != 0) {
            return new Money(this.value + Math.abs(credito.value));
        }
        return this;
    }

    public Money divisaoArredondandoTeto(long escalar) {
        Money res = this;
        long tmpValro = (long) Math.ceil((double) this.value / escalar);
        res = new Money(tmpValro);
        return res;
    }

    public Money[] divide(int quantidadeRestante) {
        if (quantidadeRestante == 0) {
            throw new IllegalArgumentException("Divisão por zero não é permitida");
        }
        Money[] res = new Money[quantidadeRestante];
        long tmpValor = this.value / quantidadeRestante;
        long restante = this.value % quantidadeRestante;
        for (int i = 0; i < quantidadeRestante - 1; i++) {
            res[i] = new Money(tmpValor);
        }
        res[quantidadeRestante - 1] = new Money(tmpValor + restante);
        return res;
    }

    public long getEscala() {
        return FATOR_UNIDADE;
    }

    public BigDecimal getValorBig() {
        BigDecimal bigDecimal = new BigDecimal(value);
//        bigDecimal = bigDecimal.setScale(2, RoundingMode.DOWN);
        bigDecimal = bigDecimal.movePointLeft(2);
        return bigDecimal;
    }

//    public static void main(String... args) {
//        Money money = new Money(1000);
//        System.out.println("Money: " + money.formatarValor());
//        System.out.println("BD:" + money.getValorBig());
//        String format = new DecimalFormat("¤ #,##0.00").format(money.getValorBig());
//        System.out.println("DecimalFormat: " + format);
//    }
    @Override
    public boolean isZero() {
        return value == 0;
    }

    @Override
    public MonetaryContext getContext() {
        return delegate.getContext();
    }

    @Override
    public MonetaryAmountFactory<? extends MonetaryAmount> getFactory() {
        return delegate.getFactory();
    }

    @Override
    public boolean isGreaterThan(MonetaryAmount arg0) {
        return delegate.isGreaterThan(arg0);
    }

    @Override
    public boolean isGreaterThanOrEqualTo(MonetaryAmount arg0) {
        return delegate.isGreaterThanOrEqualTo(arg0);
    }

    @Override
    public boolean isLessThan(MonetaryAmount arg0) {
        return delegate.isLessThan(arg0);
    }

    @Override
    public boolean isLessThanOrEqualTo(MonetaryAmount arg0) {
        return delegate.isLessThanOrEqualTo(arg0);
    }

    @Override
    public boolean isEqualTo(MonetaryAmount arg0) {
        return delegate.isEqualTo(arg0);
    }

    @Override
    public int signum() {
        return delegate.signum();
    }

    @Override
    public MonetaryAmount add(MonetaryAmount arg0) {
        return delegate.add(arg0);
    }

    @Override
    public MonetaryAmount subtract(MonetaryAmount arg0) {
        return delegate.subtract(arg0);
    }

    @Override
    public MonetaryAmount multiply(long arg0) {
        return delegate.multiply(arg0);
    }

    @Override
    public MonetaryAmount multiply(double arg0) {
        return delegate.multiply(arg0);
    }

    @Override
    public MonetaryAmount multiply(Number arg0) {
        return delegate.multiply(arg0);
    }

    @Override
    public MonetaryAmount divide(long arg0) {
        return delegate.divide(arg0);
    }

    @Override
    public MonetaryAmount divide(double arg0) {
        return delegate.divide(arg0);
    }

    @Override
    public MonetaryAmount divide(Number arg0) {
        return delegate.divide(arg0);
    }

    @Override
    public MonetaryAmount remainder(long arg0) {
        return delegate.remainder(arg0);
    }

    @Override
    public MonetaryAmount remainder(double arg0) {
        return delegate.remainder(arg0);
    }

    @Override
    public MonetaryAmount remainder(Number arg0) {
        return delegate.remainder(arg0);
    }

    @Override
    public MonetaryAmount[] divideAndRemainder(long arg0) {
        return delegate.divideAndRemainder(arg0);
    }

    @Override
    public MonetaryAmount[] divideAndRemainder(double arg0) {
        return delegate.divideAndRemainder(arg0);
    }

    @Override
    public MonetaryAmount[] divideAndRemainder(Number arg0) {
        return delegate.divideAndRemainder(arg0);
    }

    @Override
    public MonetaryAmount divideToIntegralValue(long arg0) {
        return delegate.divideToIntegralValue(arg0);
    }

    @Override
    public MonetaryAmount divideToIntegralValue(double arg0) {
        return delegate.divideToIntegralValue(arg0);
    }

    @Override
    public MonetaryAmount divideToIntegralValue(Number arg0) {
        return delegate.divideToIntegralValue(arg0);
    }

    @Override
    public MonetaryAmount scaleByPowerOfTen(int arg0) {
        return delegate.scaleByPowerOfTen(arg0);
    }

    @Override
    public MonetaryAmount abs() {
        return delegate.abs();
    }

    @Override
    public MonetaryAmount negate() {
        return delegate.negate();
    }

    @Override
    public MonetaryAmount plus() {
        return delegate.plus();
    }

    @Override
    public MonetaryAmount stripTrailingZeros() {
        return delegate.stripTrailingZeros();
    }

    @Override
    public CurrencyUnit getCurrency() {
        return delegate.getCurrency();
    }

    @Override
    public NumberValue getNumber() {
        return delegate.getNumber();
    }
}
