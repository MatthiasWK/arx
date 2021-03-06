/*
 * ARX: Efficient, Stable and Optimal Data Anonymization
 * Copyright (C) 2012 - 2014 Florian Kohlmayer, Fabian Prasser
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.deidentifier.arx.gui.worker;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.deidentifier.arx.ARXLattice;
import org.deidentifier.arx.ARXLattice.ARXNode;
import org.deidentifier.arx.AttributeType;
import org.deidentifier.arx.AttributeType.Hierarchy;
import org.deidentifier.arx.DataDefinition;
import org.deidentifier.arx.DataHandle;
import org.deidentifier.arx.DataType;
import org.deidentifier.arx.DataType.DataTypeWithFormat;
import org.deidentifier.arx.criteria.PrivacyCriterion;
import org.deidentifier.arx.gui.Controller;
import org.deidentifier.arx.gui.model.Model;
import org.deidentifier.arx.gui.model.ModelConfiguration;
import org.deidentifier.arx.gui.resources.Resources;
import org.deidentifier.arx.gui.worker.io.FileBuilder;
import org.deidentifier.arx.gui.worker.io.Vocabulary;
import org.deidentifier.arx.gui.worker.io.Vocabulary_V2;
import org.deidentifier.arx.gui.worker.io.XMLWriter;
import org.deidentifier.arx.io.CSVDataOutput;
import org.deidentifier.arx.metric.InformationLoss;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * This worker saves a project file to disk
 * @author Fabian Prasser
 */
public class WorkerSave extends Worker<Model> {

    /** The vocabulary to use*/
    private Vocabulary vocabulary = new Vocabulary_V2();
	/** The path*/
    private final String     path;
    /** The model*/
    private final Model      model;

    /**
     * Creates a new instance
     * @param path
     * @param controller
     * @param model
     */
    public WorkerSave(final String path,
                      final Controller controller,
                      final Model model) {
        this.path = path;
        this.model = model;
    }

    @Override
    public void run(final IProgressMonitor arg0) throws InvocationTargetException,
                                                        InterruptedException {

        arg0.beginTask(Resources.getMessage("WorkerSave.0"), 10); //$NON-NLS-1$

        try {
            final FileOutputStream f = new FileOutputStream(path);
            final ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(f));
            model.createConfig(); 
            writeMetadata(model, zip);
            arg0.worked(1);
            writeModel(model, zip);
            arg0.worked(2);
            writeInput(model, zip);
            arg0.worked(4);
            writeOutput(model, zip);
            arg0.worked(5);
            writeOutputSubset(model, zip);
            arg0.worked(6);
            writeConfiguration(model, zip);
            arg0.worked(7);
            final Map<String, Integer> map = writeLattice(model, zip);
            arg0.worked(8);
            writeClipboard(model, map, zip);
            arg0.worked(9);
            writeFilter(model, zip);
            zip.close();
            arg0.worked(10);
        } catch (final Exception e) {
            error = e;
            arg0.done();
            return;
        }

        arg0.worked(100);
        arg0.done();
    }

    /**
     * Converts an attribute name to a file name
     * 
     * @param a
     * @return
     */
    private String toFileName(final String a) {
        return a;
    }

    /**
     * Converts a configuration to XML
     * 
     * @param model
     * @return
     * @throws IOException 
     */
    private String toXML(final ModelConfiguration config) throws IOException {
        
    	XMLWriter writer = new XMLWriter(); 
        writer.indent(vocabulary.getConfig());
        writer.write(vocabulary.getRemoveOutliers(), config.isRemoveOutliers());
        writer.write(vocabulary.getPracticalMonotonicity(), config.isPracticalMonotonicity());
        writer.write(vocabulary.getProtectSensitiveAssociations(), config.isProtectSensitiveAssociations());
        writer.write(vocabulary.getRelativeMaxOutliers(), config.getAllowedOutliers());
        writer.write(vocabulary.getMetric(), config.getMetric().getClass().getSimpleName());
        writer.indent(vocabulary.getCriteria());
        for (PrivacyCriterion c : config.getCriteria()) {
        	if (c != null) {
        		writer.write(vocabulary.getCriterion(), c.toString());
        	}
        }
        writer.unindent();
        writer.unindent();
        return writer.toString();
    }
    
    /**
     * Returns an XML representation of the data definition
     * @param config 
     * 
     * @param handle
     * @param definition
     * @return
     * @throws IOException 
     */
    private String toXML(final ModelConfiguration config, 
                         final DataHandle handle,
                         final DataDefinition definition) throws IOException {
        
    	XMLWriter writer = new XMLWriter();
    	writer.indent(vocabulary.getDefinition());
        for (int i = 0; i < handle.getNumColumns(); i++) {
            final String attr = handle.getAttributeName(i);
            AttributeType t = definition.getAttributeType(attr);
            DataType<?> dt = definition.getDataType(attr);
            if (t == null) t = AttributeType.IDENTIFYING_ATTRIBUTE;
            if (dt == null) dt = DataType.STRING;
            
            writer.indent(vocabulary.getAssigment());
            writer.write(vocabulary.getName(), attr);
            writer.write(vocabulary.getType(), t.toString());
            writer.write(vocabulary.getDatatype(), dt.getDescription().getLabel());
            if (dt.getDescription().hasFormat()){
                String format = ((DataTypeWithFormat)dt).getFormat();
                if (format != null){
                    writer.write(vocabulary.getFormat(), format);
                }
            }
            
            if (t instanceof Hierarchy || 
                (t == AttributeType.SENSITIVE_ATTRIBUTE && config.getHierarchy(attr)!=null)) {
            	writer.write(vocabulary.getRef(), "hierarchies/" + toFileName(attr) + ".csv"); //$NON-NLS-1$ //$NON-NLS-2$
                if (t instanceof Hierarchy){
                    Integer min = config.getMinimumGeneralization(attr);
                    Integer max = config.getMaximumGeneralization(attr);
                	writer.write(vocabulary.getMin(), min==null ? "All" : String.valueOf(min));
                	writer.write(vocabulary.getMax(), max==null ? "All" : String.valueOf(max));
                }
            }
            writer.unindent();

        }
        writer.unindent();
        return writer.toString();
    }

    /**
     * Returns an XML representation of the lattice
     * 
     * @param map
     * @param l
     * @param zip
     * @return
     * @throws IOException
     */
    private void toXML(final Map<String, Integer> map,
                       final ARXLattice l,
                       final ZipOutputStream zip) throws IOException {

        // Build mapping
        int id = 0;
        for (final ARXNode[] level : l.getLevels()) {
            for (final ARXNode n : level) {
                final String key = Arrays.toString(n.getTransformation());
                if (!map.containsKey(key)) {
                    map.put(key, id++);
                }
            }
        }

        // Write directly because of size
        final FileBuilder b = new FileBuilder(new OutputStreamWriter(zip));
        final XMLWriter writer = new XMLWriter(b);
        
        writer.write(vocabulary.getHeader());

        // Build xml
        writer.indent(vocabulary.getLattice());
        for (int i = 0; i < l.getLevels().length; i++) {
        	
        	writer.indent(vocabulary.getLevel(), vocabulary.getDepth(), i);
            for (final ARXNode n : l.getLevels()[i]) {
                
            	final String key = Arrays.toString(n.getTransformation());
                final int currentId = map.get(key);
                
                writer.indent(vocabulary.getNode2(), vocabulary.getId(), currentId);
                writer.write(vocabulary.getTransformation(), n.getTransformation());
                writer.write(vocabulary.getAnonymity(), n.isAnonymous());
                writer.write(vocabulary.getChecked(), n.isChecked());
                if (n.getPredecessors().length > 0) {
                	writer.write(vocabulary.getPredecessors(), n.getPredecessors(), map);
                }
                if (n.getSuccessors().length > 0) {
                	writer.write(vocabulary.getSuccessors(), n.getSuccessors(), map);
                }
                writer.indent(vocabulary.getInfoloss());
                writer.write(vocabulary.getMax2(), n.getMaximumInformationLoss().getValue());
                writer.write(vocabulary.getMin2(), n.getMinimumInformationLoss().getValue());
                writer.unindent();
                writer.unindent();
            }
            writer.unindent();
        }
        writer.unindent();
        b.flush();
    }

    /**
     * Returns an XML representation of the clipboard
     * 
     * @param map
     * @param clipboard
     * @return
     * @throws IOException 
     */
    private String toXML(final Map<String, Integer> map,
                         final Set<ARXNode> clipboard) throws IOException {

        XMLWriter writer = new XMLWriter();
        writer.indent(vocabulary.getClipboard()); //$NON-NLS-1$
        for (final ARXNode n : clipboard) {
        	writer.write(vocabulary.getNode(), Arrays.toString(n.getTransformation())); //$NON-NLS-1$
        }
        writer.unindent();
        return writer.toString();
    }

    /**
     * Converts a model to XML
     * 
     * @param model
     * @return
     * @throws IOException 
     */
    private String toXML(final Model model) throws IOException {
    	
        XMLWriter writer = new XMLWriter();
        writer.indent(vocabulary.getProject());
        writer.write(vocabulary.getName(), model.getName());
        writer.write(vocabulary.getSeparator(), model.getSeparator());
        writer.write(vocabulary.getDescription(), model.getDescription());
        writer.write(vocabulary.getSuppressionString(), model.getSuppressionString());
        writer.write(vocabulary.getHistorySize(), model.getHistorySize());
        writer.write(vocabulary.getSnapshotSizeDataset(), model.getSnapshotSizeDataset());
        writer.write(vocabulary.getSnapshotSizeSnapshot(), model.getSnapshotSizeSnapshot());
        writer.write(vocabulary.getInitialNodesInViewer(), model.getInitialNodesInViewer());
        writer.write(vocabulary.getMaxNodesInLattice(), model.getMaxNodesInLattice());
        writer.write(vocabulary.getMaxNodesInViewer(), model.getMaxNodesInViewer());
        writer.write(vocabulary.getSelectedAttribute(), model.getSelectedAttribute());
        writer.write(vocabulary.getInputBytes(), model.getInputBytes());
        writer.unindent();
        return writer.toString();
    }

    /**
     * Writes the clipboard to the file
     * 
     * @param map
     * @param zip
     * @throws IOException
     */
    private void writeClipboard(final Model model,
                                final Map<String, Integer> map,
                                final ZipOutputStream zip) throws IOException {
        if ((model.getClipboard() == null) || model.getClipboard().isEmpty()) { return; }

        // Write clipboard
        zip.putNextEntry(new ZipEntry("clipboard.xml")); //$NON-NLS-1$
        final Writer w = new OutputStreamWriter(zip);
        w.write(toXML(map, model.getClipboard()));
        w.flush();

    }

    /**
     * Writes the configuration to the file
     * 
     * @param zip
     * @throws IOException
     */
    private void writeConfiguration(final ModelConfiguration config,
                                    final String prefix,
                                    final ZipOutputStream zip) throws IOException {
    	
        zip.putNextEntry(new ZipEntry(prefix + "config.dat")); //$NON-NLS-1$
        final ObjectOutputStream oos = new ObjectOutputStream(zip);
        oos.writeObject(config);
        oos.flush();

        zip.putNextEntry(new ZipEntry(prefix + "config.xml")); //$NON-NLS-1$
        final Writer w = new OutputStreamWriter(zip);
        w.write(toXML(config));
        w.flush();

        writeDefinition(config, prefix, zip);
        writeHierarchies(config, prefix, zip);
    }

    /**
     * Writes the configuration to the file
     * 
     * @param zip
     * @throws IOException
     */
    private void writeConfiguration(final Model model, final ZipOutputStream zip) throws IOException {

        if (model.getInputConfig() != null) {
            writeConfiguration(model.getInputConfig(), "input/", zip); //$NON-NLS-1$
        }
        if (model.getOutputConfig() != null) {
            writeConfiguration(model.getOutputConfig(), "output/", zip); //$NON-NLS-1$
        }
    }

    /**
     * Writes the data definition to the file
     * 
     * @param zip
     * @throws IOException
     */
    private void writeDefinition(final ModelConfiguration config,
                                 final String prefix,
                                 final ZipOutputStream zip) throws IOException {
    	
    	// Obtain definition
    	DataDefinition definition = null;
    	if (config == model.getInputConfig()) definition = model.getInputDefinition();
    	else definition = model.getOutputDefinition();
    	
    	// Store
		if (definition != null) {
			zip.putNextEntry(new ZipEntry(prefix + "definition.xml")); //$NON-NLS-1$
			final Writer w = new OutputStreamWriter(zip);
			w.write(toXML(config, config.getInput().getHandle(), definition));
			w.flush();
		}
    }

    /**
     * Writes the current filter to the file
     * 
     * @param zip
     * @throws IOException
     */
    private void writeFilter(final Model model, final ZipOutputStream zip) throws IOException {
        if ((model.getAnonymizer() == null) || (model.getResult() == null)) { return; }
        zip.putNextEntry(new ZipEntry("filter.dat")); //$NON-NLS-1$
        final ObjectOutputStream oos = new ObjectOutputStream(zip);
        oos.writeObject(model.getNodeFilter());
        oos.flush();
    }
    
    /**
     * Writes the hierarchies to the file
     * 
     * @param zip
     * @throws IOException
     */
    private void writeHierarchies(final ModelConfiguration config,
                                  final String prefix,
                                  final ZipOutputStream zip) throws IOException {

        for (Entry<String, Hierarchy> entry : config.getHierarchies().entrySet()) {
            zip.putNextEntry(new ZipEntry(prefix + "hierarchies/" + toFileName(entry.getKey()) + ".csv")); //$NON-NLS-1$ //$NON-NLS-2$
            final CSVDataOutput out = new CSVDataOutput(zip, model.getSeparator());
            out.write(entry.getValue().getHierarchy());
        }
    }

    /**
     * Writes the input to the file
     * 
     * @param zip
     * @throws IOException
     */
    private void writeInput(final Model model, final ZipOutputStream zip) throws IOException {
        if (model.getInputConfig().getInput() != null) {
            if (model.getInputConfig().getInput().getHandle() != null) {
                zip.putNextEntry(new ZipEntry("data/input.csv")); //$NON-NLS-1$
                final CSVDataOutput out = new CSVDataOutput(zip, model.getSeparator());
                out.write(model.getInputConfig()
                               .getInput()
                               .getHandle()
                               .iterator());
            }
        }
    }
    
    /**
     * Writes the lattice to the file
     * 
     * @param zip
     * @return
     * @throws IOException
     */
    private Map<String, Integer> writeLattice(final Model model, final ZipOutputStream zip) throws IOException {

        // Mapping
        final Map<String, Integer> map = new HashMap<String, Integer>();
        if ((model.getResult() == null) ||
            (model.getResult().getLattice() == null)) { return map; }

        // Write lattice
        final ARXLattice l = model.getResult().getLattice();
        zip.putNextEntry(new ZipEntry("lattice.xml")); //$NON-NLS-1$
        toXML(map, l, zip);

        zip.putNextEntry(new ZipEntry("lattice.dat")); //$NON-NLS-1$
        ObjectOutputStream oos = new ObjectOutputStream(zip);
        oos.writeObject(model.getResult().getLattice());
        oos.writeObject(model.getResult()
                             .getLattice()
                             .access()
                             .getAttributeMap());
        oos.flush();

        // Write information loss
        zip.putNextEntry(new ZipEntry("infoloss.dat")); //$NON-NLS-1$
        final Map<Integer, InformationLoss> max = new HashMap<Integer, InformationLoss>();
        final Map<Integer, InformationLoss> min = new HashMap<Integer, InformationLoss>();
        for (final ARXNode[] level : l.getLevels()) {
            for (final ARXNode n : level) {
                final String key = Arrays.toString(n.getTransformation());
                min.put(map.get(key), n.getMinimumInformationLoss());
                max.put(map.get(key), n.getMaximumInformationLoss());
            }
        }
        oos = new ObjectOutputStream(zip);
        oos.writeObject(min);
        oos.writeObject(max);
        oos.flush();
        min.clear();
        max.clear();

        // Write attributes
        zip.putNextEntry(new ZipEntry("attributes.dat")); //$NON-NLS-1$
        final Map<Integer, Map<Integer, Object>> attrs = new HashMap<Integer, Map<Integer, Object>>();
        for (final ARXNode[] level : l.getLevels()) {
            for (final ARXNode n : level) {
                final String key = Arrays.toString(n.getTransformation());
                attrs.put(map.get(key), n.getAttributes());
            }
        }
        oos = new ObjectOutputStream(zip);
        oos.writeObject(attrs);
        oos.flush();
        attrs.clear();

        // Return mapping
        return map;
    }

    /**
     * Writes the meta data to the file
     * 
     * @param map
     * @param zip
     * @throws IOException
     */
    private void writeMetadata(final Model model, final ZipOutputStream zip) throws IOException {
    	
        // Write metadata
        zip.putNextEntry(new ZipEntry("metadata.xml"));
        final OutputStreamWriter w = new OutputStreamWriter(zip);
        XMLWriter writer = new XMLWriter(new FileBuilder(w));
        writer.indent(vocabulary.getMetadata());
        writer.write(vocabulary.getVersion(), Resources.getVersion());
        writer.write(vocabulary.getVocabulary(), vocabulary.getVocabularyVersion());
        writer.unindent();
        w.flush();

    }

    /**
     * Writes the project to the file
     * 
     * @param zip
     * @throws IOException
     */
    private void writeModel(final Model model, final ZipOutputStream zip) throws IOException {
        zip.putNextEntry(new ZipEntry("project.dat")); //$NON-NLS-1$
        final ObjectOutputStream oos = new ObjectOutputStream(zip);
        oos.writeObject(model);
        oos.flush();

        zip.putNextEntry(new ZipEntry("project.xml")); //$NON-NLS-1$
        final Writer w = new OutputStreamWriter(zip);
        w.write(toXML(model));
        w.flush();
    }

	/**
	 * Writes the output to the file
	 * 
	 * @param zip
	 * @throws IOException
	 */
	private void writeOutput(final Model model, final ZipOutputStream zip) throws IOException {
		if (model.getOutput() != null) {
			zip.putNextEntry(new ZipEntry("data/output.csv")); //$NON-NLS-1$
			final CSVDataOutput out = new CSVDataOutput(zip, model.getSeparator());
			out.write(model.getOutput().iterator());
		}
	}

    /**
     * Writes the output to the file
     * 
     * @param zip
     * @throws IOException
     */
    private void writeOutputSubset(final Model model, final ZipOutputStream zip) throws IOException {
        if (model.getOutput() != null) {
            zip.putNextEntry(new ZipEntry("data/output_subset.csv")); //$NON-NLS-1$
            final CSVDataOutput out = new CSVDataOutput(zip, model.getSeparator());
            out.write(model.getOutput().getView().iterator());
        }
    }
}
