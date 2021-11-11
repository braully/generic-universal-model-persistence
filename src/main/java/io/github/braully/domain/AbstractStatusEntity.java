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

import io.github.braully.constant.Attr;
import io.github.braully.persistence.IEntity;
import io.github.braully.persistence.IEntityStatus;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@MappedSuperclass
public abstract class AbstractStatusEntity extends AbstractEntity
        implements IEntity, IEntityStatus, Serializable {

    @Attr("hidden")
    @Column(name = "status",
            columnDefinition = "integer default '0'")
    @Enumerated(EnumType.ORDINAL)
    protected Integer status;

    @Override
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean isPersisted() {
        return this.id != null && this.id > 0;
    }
}
