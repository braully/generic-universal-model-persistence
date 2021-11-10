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
package com.github.braully.persistence;

import java.util.Date;

/**
 *
 * @author braullyrocha
 */
public interface IEntityAuditable extends IEntity {

    public void setIdUser(Long id);

    public void setDate(Date date);

    public void auditEntity(ISecurityContext isecurityContext);
}
