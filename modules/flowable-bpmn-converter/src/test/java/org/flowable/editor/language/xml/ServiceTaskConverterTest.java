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
package org.flowable.editor.language.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.flowable.bpmn.model.FlowableListener;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FieldExtension;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.ImplementationType;
import org.flowable.bpmn.model.ServiceTask;
import org.junit.Test;

public class ServiceTaskConverterTest extends AbstractConverterTest {

    @Test
    public void convertXMLToModel() throws Exception {
        BpmnModel bpmnModel = readXMLFile();
        validateModel(bpmnModel);
    }

    @Test
    public void convertModelToXML() throws Exception {
        BpmnModel bpmnModel = readXMLFile();
        BpmnModel parsedModel = exportAndReadXMLFile(bpmnModel);
        validateModel(parsedModel);
    }

    @Override
    protected String getResource() {
        return "servicetaskmodel.bpmn";
    }

    private void validateModel(BpmnModel model) {
        FlowElement flowElement = model.getMainProcess().getFlowElement("servicetask");
        assertNotNull(flowElement);
        assertTrue(flowElement instanceof ServiceTask);
        assertEquals("servicetask", flowElement.getId());
        ServiceTask serviceTask = (ServiceTask) flowElement;
        assertEquals("servicetask", serviceTask.getId());
        assertEquals("Service task", serviceTask.getName());

        List<FieldExtension> fields = serviceTask.getFieldExtensions();
        assertEquals(2, fields.size());
        FieldExtension field = fields.get(0);
        assertEquals("testField", field.getFieldName());
        assertEquals("test", field.getStringValue());
        field = fields.get(1);
        assertEquals("testField2", field.getFieldName());
        assertEquals("${test}", field.getExpression());

        List<FlowableListener> listeners = serviceTask.getExecutionListeners();
        assertEquals(3, listeners.size());
        FlowableListener listener = listeners.get(0);
        assertEquals(ImplementationType.IMPLEMENTATION_TYPE_CLASS, listener.getImplementationType());
        assertEquals("org.test.TestClass", listener.getImplementation());
        assertEquals("start", listener.getEvent());
        listener = listeners.get(1);
        assertEquals(ImplementationType.IMPLEMENTATION_TYPE_EXPRESSION, listener.getImplementationType());
        assertEquals("${testExpression}", listener.getImplementation());
        assertEquals("end", listener.getEvent());
        listener = listeners.get(2);
        assertEquals(ImplementationType.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION, listener.getImplementationType());
        assertEquals("${delegateExpression}", listener.getImplementation());
        assertEquals("start", listener.getEvent());

        assertEquals("R5/PT5M", serviceTask.getFailedJobRetryTimeCycleValue());
    }
}
