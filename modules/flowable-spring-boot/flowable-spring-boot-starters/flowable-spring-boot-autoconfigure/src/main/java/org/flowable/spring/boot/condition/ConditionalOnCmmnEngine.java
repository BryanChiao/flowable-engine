/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.flowable.spring.boot.condition;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * A meta {@link org.springframework.context.annotation.Conditional} annotation that checks if the CMMN engine
 * should / can be activated.
 * <p>
 * By default the cmmn engine is activated when the {@link org.flowable.cmmn.engine.CmmnEngine} and
 * {@link org.flowable.cmmn.spring.SpringCmmnEngineConfiguration} are present.
 * Additionally the property {@code flowable.cmmn.enabled} is checked, if it is {@code true} or missing
 * the cmmn engine would be enabled.
 *
 * @author Filip Hrisafov
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Documented
@ConditionalOnClass(name = {
    "org.flowable.cmmn.engine.CmmnEngine",
    "org.flowable.cmmn.spring.SpringCmmnEngineConfiguration"
})
@ConditionalOnProperty(prefix = "flowable.cmmn", name = "enabled", havingValue = "true", matchIfMissing = true)
public @interface ConditionalOnCmmnEngine {

}
