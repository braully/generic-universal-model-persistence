/*
 Copyright 2109 Braully Rocha

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 
 */
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

import java.util.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 *
 * @author braullyrocha
 */
@Entity
@Getter
@Setter @Accessors(chain = true)
@Table(schema = "base")
public class LogEntryError extends AbstractEntity {

    @Column(length = 30)
    protected String app;
    @Column(length = 10)
    protected String ver;
    @Column
    protected Integer revision;
    @Column(length = 10)
    protected String level;
    @Column(length = 150)
    protected String file;
    @Column(length = 150)
    protected String location;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    protected Date date;
    @Column(length = 255)
    protected String message;
    @Column(columnDefinition = "TEXT")
    protected String throwable;

    public LogEntryError() {
    }

    public LogEntryError(String app, String version, Integer revision, String level, String file, String location, Date date, String message) {
        this.app = app;
        this.ver = version;
        this.revision = revision;
        this.level = level;
        this.file = file;
        this.location = location;
        this.date = date;
        this.message = message;
    }
}
