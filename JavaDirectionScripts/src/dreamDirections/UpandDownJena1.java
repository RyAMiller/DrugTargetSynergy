package dreamDirections;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;

@SuppressWarnings("unused")

public class UpandDownJena1 {

public static void main( String[] args ) throws IOException {
        
    	//setting the output file source
    	File outFile = new File("./DreamOutFileTest.txt");
    	File firstOut = new File("./firstOutTest.txt");
    	
    	//checking to see if output file exists and if it does not, 
    	//create a new file with this name.
    	if (!outFile.exists()) {
			try {
				outFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    	if (!firstOut.exists()) {
			try {
				firstOut.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    	
    	// create new filehandles to be written to
    	FileWriter fw = new FileWriter(outFile.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		
    	FileWriter fw1 = new FileWriter(firstOut.getAbsoluteFile());
		BufferedWriter bw1 = new BufferedWriter(fw1);
		
		// some column headers for output file
		bw.write("OriginalPoint\tfirstDown\tlabel\tSecondDown\tlabel\tThirdDown\t"
				+ "label\tFourthDown\n");
		bw1.write("OriginalPoint\tfirstDown\tlabel\tsecondDown\tlabel\tthirdDown\n");
		
    	//first query to ChEMBL to get ChEMBL target IDs for FDA approved drugs
    	String s2 = 
    			"PREFIX wp:      <http://vocabularies.wikipathways.org/wp#>\n"+
    			"PREFIX rdfs:    <http://www.w3.org/2000/01/rdf-schema#>\n"+
    			"PREFIX dcterms: <http://purl.org/dc/terms/>\n"+
//    			"PREFIX xsd:     <http://www.w3.org/2001/XMLSchema#>\n"+
    			"PREFIX rdf:     <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"+
    			"PREFIX dc:      <http://purl.org/dc/elements/1.1/>\n"+
    			
    			"SELECT DISTINCT ?geneProduct ?targetDown ?label2 ?targetDown2 ?label3 \n"+
    			"WHERE {\n"+
    			"  ?geneProduct rdf:type wp:GeneProduct . \n"+
    			"  ?geneProduct rdfs:label ?labelOrigin .\n"+
    			"  ?geneProduct dcterms:isPartOf ?pathway .\n"+
    			"  ?pathway rdf:type wp:Pathway .\n"+
    			"  ?geneProduct dcterms:isPartOf ?anInteraction . \n"+
    			"  ?anInteraction rdf:type wp:DirectedInteraction . \n"+
    			"  ?pathway wp:organism  <http://purl.obolibrary.org/obo/NCBITaxon_9606> .\n"+

//    			" ?pathway dc:identifier ?pURI .\n"+

//    			"OPTIONAL\n"+
//    			"{\n"+
    			"  ?sourceDown dc:identifier ?geneProduct .\n"+

    			"  ?interactionDown rdf:type wp:DirectedInteraction .\n"+
    			"  ?interactionDown wp:source ?sourceDown .\n"+
    			"  ?interactionDown wp:target ?targetDown .\n"+

    			"  ?sourceDown rdfs:label ?labelOrigin .\n"+
    			"  ?targetDown rdfs:label ?label2 .\n"+
//    			"}\n"+

    			"OPTIONAL\n"+
    			"{\n"+
    			"  ?sourceDown1 dc:identifier ?targetDown .\n"+

				"  ?interactionDown2 rdf:type wp:DirectedInteraction .\n"+
				"  ?interactionDown2 wp:source ?sourceDown1 .\n"+
				"  ?interactionDown2 wp:target ?targetDown2 .\n"+

//				"  ?sourceDown rdfs:label ?label2 .\n"+
				"  ?targetDown2 rdfs:label ?label3 .\n"+
    			"}\n"+
    				    
    			"FILTER (regex(str(?labelOrigin), '^EGFR$') || regex(str(?labelOrigin), '^CD19 antibody$') || regex(str(?labelOrigin), '^AKT1$') || regex(str(?labelOrigin), '^AKT2$') || regex(str(?labelOrigin), '^AKT*') || regex(str(?labelOrigin), '^PIK3C') || regex(str(?labelOrigin), '^SGK*') || regex(str(?labelOrigin), '^ALK$') || regex(str(?labelOrigin), '^IGF*R$') || regex(str(?labelOrigin), '^IGFR$') || regex(str(?labelOrigin), '^AR$') || regex(str(?labelOrigin), '^ATM$') || regex(str(?labelOrigin), '^ATR$') || regex(str(?labelOrigin), '^BCL2$') || regex(str(?labelOrigin), '^AKT3$') || regex(str(?labelOrigin), '^BCL2L1$') || regex(str(?labelOrigin), '^BCL2L2$') || regex(str(?labelOrigin), '^BIRC2$') || regex(str(?labelOrigin), '^BIRC3$') || regex(str(?labelOrigin), '^BIRC5$') || regex(str(?labelOrigin), '^BRAF$') || regex(str(?labelOrigin), '^BRAF_mut$') || regex(str(?labelOrigin), '^BRAF_V600E$') || regex(str(?labelOrigin), '^CHEK1$') || regex(str(?labelOrigin), '^CHK1$') || regex(str(?labelOrigin), '^CHUK$') || regex(str(?labelOrigin), '^cMET$') || regex(str(?labelOrigin), '^CMPK1$') || regex(str(?labelOrigin), '^CSNK2A1$') || regex(str(?labelOrigin), '^DNA$') || regex(str(?labelOrigin), '^DNMT1$') || regex(str(?labelOrigin), '^ERBB') || regex(str(?labelOrigin), '^ESR1$') || regex(str(?labelOrigin), '^FASN$') || regex(str(?labelOrigin), '^FGFR1$') || regex(str(?labelOrigin), '^FGFR2$') || regex(str(?labelOrigin), '^FGFR3$') || regex(str(?labelOrigin), '^FGFR4$') || regex(str(?labelOrigin), '^FLT1$') || regex(str(?labelOrigin), '^FLT4$') || regex(str(?labelOrigin), '^FNTA$') || regex(str(?labelOrigin), '^FNTB$') || regex(str(?labelOrigin), '^Gamma secretase$') || regex(str(?labelOrigin), '^HDAC*') || regex(str(?labelOrigin), '^HDAC1$') || regex(str(?labelOrigin), '^HDAC3$') || regex(str(?labelOrigin), '^HSP90AA1$') || regex(str(?labelOrigin), '^IGF*R$') || regex(str(?labelOrigin), '^IKBK*') || regex(str(?labelOrigin), '^JAK1$') || regex(str(?labelOrigin), '^JAK2$') || regex(str(?labelOrigin), '^KDR$') || regex(str(?labelOrigin), '^KIF11$') || regex(str(?labelOrigin), '^MAP2$') || regex(str(?labelOrigin), '^MAP2K*$') || regex(str(?labelOrigin), '^MAP2K1$') || regex(str(?labelOrigin), '^MAP2K2$') || regex(str(?labelOrigin), '^Methylation$') || regex(str(?labelOrigin), '^microtubule$') || regex(str(?labelOrigin), '^MTOR$') || regex(str(?labelOrigin), '^NAE1$') || regex(str(?labelOrigin), '^NAE2$') || regex(str(?labelOrigin), '^MAP4$') || regex(str(?labelOrigin), '^NIAP$') || regex(str(?labelOrigin), '^MAPT$') || regex(str(?labelOrigin), '^MET$') || regex(str(?labelOrigin), '^NR1I2$') || regex(str(?labelOrigin), '^PARP1$') || regex(str(?labelOrigin), '^PARP6$') || regex(str(?labelOrigin), '^PDGFRA$') || regex(str(?labelOrigin), '^PIK3C*$') || regex(str(?labelOrigin), '^PIK3CA$') || regex(str(?labelOrigin), '^PIK3CB$') || regex(str(?labelOrigin), '^PIK3CD$') || regex(str(?labelOrigin), '^PIM1$') || regex(str(?labelOrigin), '^PIP5K1*$') || regex(str(?labelOrigin), '^PRKC*$') || regex(str(?labelOrigin), '^PRKDC$') || regex(str(?labelOrigin), '^Proteasome$') || regex(str(?labelOrigin), '^PTK2*$') || regex(str(?labelOrigin), '^RAF1$') || regex(str(?labelOrigin), '^RET$') || regex(str(?labelOrigin), '^RRM1$') || regex(str(?labelOrigin), '^SLC16A3$') || regex(str(?labelOrigin), '^SLC16A4$') || regex(str(?labelOrigin), '^SRC$') || regex(str(?labelOrigin), '^SYK$') || regex(str(?labelOrigin), '^TEK$') || regex(str(?labelOrigin), '^Thiamine$') || regex(str(?labelOrigin), '^TIE2$') || regex(str(?labelOrigin), '^TNFA$') || regex(str(?labelOrigin), '^TNFSF10$') || regex(str(?labelOrigin), '^TNKS$') || regex(str(?labelOrigin), '^TOP*$') || regex(str(?labelOrigin), '^TOP1$') || regex(str(?labelOrigin), '^TOP1MT$') || regex(str(?labelOrigin), '^TOP2*$') || regex(str(?labelOrigin), '^TTK$') || regex(str(?labelOrigin), '^TUBB$') || regex(str(?labelOrigin), '^TUBB1$') || regex(str(?labelOrigin), '^TYMS$') || regex(str(?labelOrigin), '^UBA3$') || regex(str(?labelOrigin), '^VEGFR2$') || regex(str(?labelOrigin), '^WEE1$') || regex(str(?labelOrigin), '^WNT*$') || regex(str(?labelOrigin), '^XIAP$') )  .\n" + 
    			"FILTER (?geneProduct != ?targetDown2) . \n"+
    			"FILTER (?targetDown != ?targetDown2) . \n"+
    			"FILTER (?geneProduct != ?targetDown)"+
    			"} ";

    	//Create and execute query using Jena libraries
        Query firstUpDownQuery = QueryFactory.create(s2); //s2 = the query above
//        QueryExecution qExe = QueryExecutionFactory.sparqlService(
//        "http://sparql.uniprot.org/sparql", query );
        QueryExecution qExefirstUpDown = QueryExecutionFactory.sparqlService(
        		"http://sparql.wikipathways.org/", firstUpDownQuery );
        // use execSelect for select queries
        ResultSet firstUpDownResults = qExefirstUpDown.execSelect();
//        ResultSetFormatter.out(System.out, ChemblResults) ;
        
        
        ArrayList<String> arFirstDown = new ArrayList<String>();
        Set<FirstQueryResult> arSecondDown = new HashSet<FirstQueryResult>();
        
        
        String firstDown = "";
        String label1 = "";
        String secondDown = "";
        String label2 = "";
        String original = "";
        
        
        //loops through the ChemblResults set results until end of file
        //assigns ChEMBL targets to array and writes to output file
        while (firstUpDownResults.hasNext()){
        	FirstQueryResult fqr =  new FirstQueryResult("");
        	QuerySolution firstUpDownSolution = firstUpDownResults.next();
        	if (firstUpDownSolution.get("targetDown") == null){
        		firstDown = "xxxxxxxxxxxxxxxxxxxxx";
        		             
//                arFirstDown.add(firstDown);	
                label1 = "xxxxxxxxxxxxxxxxxxxxx";
                
                if (firstUpDownSolution.get("targetDown2") == null) {
                	secondDown = "xxxxxxxxxxxxxxxxxxxxx";
//            		arSecondDown.add(secondDown);
            		label2 = "xxxxxxxxxxxxxxxxxxxxx";
                }
                else {
                	secondDown = firstUpDownSolution.get("targetDown2").toString();
                	label2 = firstUpDownSolution.get("label3").toString();
                	fqr = new FirstQueryResult(secondDown);
                	arSecondDown.add(fqr);
                }
        		
        	}
//            	original = firstUpDownSolution.get("geneProduct").toString();
//            	bw.write(original + "\t" + firstUp + "\t" + firstDown + "\n");
        	else if	(firstUpDownSolution.get("targetDown2") == null){
        		secondDown = "xxxxxxxxxxxxxxxxxxxxx";
//        		arSecondDown.add(secondDown);
        		label2 = "xxxxxxxxxxxxxxxxxxxxx";
        		if (firstUpDownSolution.get("targetDown") == null) {
        			firstDown = "xxxxxxxxxxxxxxxxxxxxx";
//                    arFirstDown.add(firstDown);	
                    label1 = "xxxxxxxxxxxxxxxxxxxxx";
        		}
        		else {
        			firstDown = firstUpDownSolution.get("targetDown").toString();
                	label1 = firstUpDownSolution.get("label2").toString();
//                	arSecondDown.add(secondDown);
        		}
        	}
        	else{
        		if(firstUpDownSolution.get("targetDown").toString().matches("(?i).*chebi.*")||
        				firstUpDownSolution.get("targetDown").toString().matches("(?i).*wikipathways.*")
        				||
        				firstUpDownSolution.get("targetDown").toString().matches("(?i).*hmdb.*")
        				||
        				firstUpDownSolution.get("targetDown").toString().matches("(?i).*pubchem.*"))
        		{}
        		else{
        			firstDown = firstUpDownSolution.get("targetDown").toString();
        			label1 = firstUpDownSolution.get("label2").toString();
//        			arFirstDown.add(firstDown);
        		}
        		if(firstUpDownSolution.get("targetDown2").toString().matches("(?i).*chebi.*")||
        				firstUpDownSolution.get("targetDown2").toString().matches("(?i).*wikipathways.*")
        				||
        				firstUpDownSolution.get("targetDown2").toString().matches("(?i).*hmdb.*")
        				||
        				firstUpDownSolution.get("targetDown2").toString().matches("(?i).*pubchem.*"))
        		{}
        		else{
        			secondDown = firstUpDownSolution.get("targetDown2").toString();
        			label2 = firstUpDownSolution.get("label3").toString();
        			fqr = new FirstQueryResult(secondDown);
        			arSecondDown.add(fqr);
        		}
        		
//        		arSecondDown.add(secondDown);
//        		arFirstDown.add(firstDown);
        		
//        		original = firstUpDownSolution.get("geneProduct").toString();
//        		bw.write(original + "\t" + firstUp + "\t" + firstDown + "\n");
        		
        		
        	}
        	original = firstUpDownSolution.get("geneProduct").toString();
//        	System.out.println(firstUp);
        	
        	bw1.write(original + "\t" + firstDown + "\t" + label1 + "\t" +  secondDown + "\t"
    				+ label2 + "\n");
        	fqr.setFirstDown(firstDown);
        	fqr.setOriginal(original);
        	fqr.setLabel1(label1);
        	fqr.setLabel2(label2);
//        	bw.write(original + "\t" + firstDown + "\t" + label1 + "\t" + secondDown + "\t" + label2 + "\n");
        	//bw.write(ChEMBLTarget+"\n");
        	
//        	ChEMBLTarget = ChemblSolution.getResource("ChEMBLTarget").getURI();
//        	toto=solution.get("ChEMBLTarget").toString();
        }
        //check to see what the output of ChEMBLTarget variable is
//        System.out.println(firstUp);
        
		
        System.out.println("Done");
        
        
        
        //loop for the elements in the ChEMBL array from the previous query 
        for (FirstQueryResult DownTwo : arSecondDown){
//        	System.out.println(DownTwo);
        	
        	//query that takes ChEMBL ID type to a Uniprot ID (that can be used in WPs)
        	String s3 = 
        			"PREFIX wp:      <http://vocabularies.wikipathways.org/wp#>\n"+
        	    			"PREFIX rdfs:    <http://www.w3.org/2000/01/rdf-schema#>\n"+
        	    			"PREFIX dcterms: <http://purl.org/dc/terms/>\n"+
        	    			"PREFIX dc:      <http://purl.org/dc/elements/1.1/>\n"+
        	    			"PREFIX rdf:     <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"+

        	    			"SELECT DISTINCT ?targetDown ?label1 ?targetDown2 ?label3 \n"+
        	    			"WHERE {\n"+
        	    			

        	    			"  <"+ DownTwo.getSecondDown() +"> dc:identifier ?downOne . \n"+  
        	    			"  ?downOne rdf:type wp:DataNode . \n"+
//        	    			"  ?downOne dcterms:isPartOf ?pathway . \n"+
//        	    			"  ?pathway rdf:type wp:Pathway . \n"+
//        	    			"  ?pathway wp:organism  <http://purl.obolibrary.org/obo/NCBITaxon_9606> . \n"+

//							"  ?downOne dcterms:isPartOf ?interactionDown . \n"+
//							"  ?interactionDown rdf:type wp:DirectedInteraction . \n"+

							"OPTIONAL\n"+
							"{\n"+
//        	    			"  ?interactionDown rdf:type wp:DirectedInteraction . \n"+
        	    			"  ?interactionDown wp:source ?downOne . \n"+
        	    			"  ?interactionDown wp:target ?targetDown . \n"+

        	    			"  ?downOne rdfs:label ?labelOrigin . \n"+
        	    			"  ?targetDown rdfs:label ?label1 . \n"+
        	    			"  } \n"+

        	    			
							"OPTIONAL\n"+
							"{\n"+
//							"  ?targetDown dc:identifier ?targetDown1 .\n"+
							
//							"  ?interactionDown2 rdf:type wp:DirectedInteraction .\n"+
							"  ?interactionDown2 wp:source ?targetDown .\n"+
							"  ?interactionDown2 wp:target ?targetDown2 .\n"+
							
							//"  ?sourceDown rdfs:label ?label2 .\n"+
							"  ?targetDown2 rdfs:label ?label3 .\n"+
							"}\n"+
								    
//"FILTER (regex(str(?labelOrigin), '^EGFR$') || regex(str(?labelOrigin), '^CD19 antibody$') || regex(str(?labelOrigin), '^AKT1$') || regex(str(?labelOrigin), '^AKT2$') || regex(str(?labelOrigin), '^AKT*') || regex(str(?labelOrigin), '^PIK3C') || regex(str(?labelOrigin), '^SGK*') || regex(str(?labelOrigin), '^ALK$') || regex(str(?labelOrigin), '^IGF*R$') || regex(str(?labelOrigin), '^IGFR$') || regex(str(?labelOrigin), '^AR$') || regex(str(?labelOrigin), '^ATM$') || regex(str(?labelOrigin), '^ATR$') || regex(str(?labelOrigin), '^BCL2$') || regex(str(?labelOrigin), '^AKT3$') || regex(str(?labelOrigin), '^BCL2L1$') || regex(str(?labelOrigin), '^BCL2L2$') || regex(str(?labelOrigin), '^BIRC2$') || regex(str(?labelOrigin), '^BIRC3$') || regex(str(?labelOrigin), '^BIRC5$') || regex(str(?labelOrigin), '^BRAF$') || regex(str(?labelOrigin), '^BRAF_mut$') || regex(str(?labelOrigin), '^BRAF_V600E$') || regex(str(?labelOrigin), '^CHEK1$') || regex(str(?labelOrigin), '^CHK1$') || regex(str(?labelOrigin), '^CHUK$') || regex(str(?labelOrigin), '^cMET$') || regex(str(?labelOrigin), '^CMPK1$') || regex(str(?labelOrigin), '^CSNK2A1$') || regex(str(?labelOrigin), '^DNA$') || regex(str(?labelOrigin), '^DNMT1$') || regex(str(?labelOrigin), '^ERBB') || regex(str(?labelOrigin), '^ESR1$') || regex(str(?labelOrigin), '^FASN$') || regex(str(?labelOrigin), '^FGFR1$') || regex(str(?labelOrigin), '^FGFR2$') || regex(str(?labelOrigin), '^FGFR3$') || regex(str(?labelOrigin), '^FGFR4$') || regex(str(?labelOrigin), '^FLT1$') || regex(str(?labelOrigin), '^FLT4$') || regex(str(?labelOrigin), '^FNTA$') || regex(str(?labelOrigin), '^FNTB$') || regex(str(?labelOrigin), '^Gamma secretase$') || regex(str(?labelOrigin), '^HDAC*') || regex(str(?labelOrigin), '^HDAC1$') || regex(str(?labelOrigin), '^HDAC3$') || regex(str(?labelOrigin), '^HSP90AA1$') || regex(str(?labelOrigin), '^IGF*R$') || regex(str(?labelOrigin), '^IKBK*') || regex(str(?labelOrigin), '^JAK1$') || regex(str(?labelOrigin), '^JAK2$') || regex(str(?labelOrigin), '^KDR$') || regex(str(?labelOrigin), '^KIF11$') || regex(str(?labelOrigin), '^MAP2$') || regex(str(?labelOrigin), '^MAP2K*$') || regex(str(?labelOrigin), '^MAP2K1$') || regex(str(?labelOrigin), '^MAP2K2$') || regex(str(?labelOrigin), '^Methylation$') || regex(str(?labelOrigin), '^microtubule$') || regex(str(?labelOrigin), '^MTOR$') || regex(str(?labelOrigin), '^NAE1$') || regex(str(?labelOrigin), '^NAE2$') || regex(str(?labelOrigin), '^MAP4$') || regex(str(?labelOrigin), '^NIAP$') || regex(str(?labelOrigin), '^MAPT$') || regex(str(?labelOrigin), '^MET$') || regex(str(?labelOrigin), '^NR1I2$') || regex(str(?labelOrigin), '^PARP1$') || regex(str(?labelOrigin), '^PARP6$') || regex(str(?labelOrigin), '^PDGFRA$') || regex(str(?labelOrigin), '^PIK3C*$') || regex(str(?labelOrigin), '^PIK3CA$') || regex(str(?labelOrigin), '^PIK3CB$') || regex(str(?labelOrigin), '^PIK3CD$') || regex(str(?labelOrigin), '^PIM1$') || regex(str(?labelOrigin), '^PIP5K1*$') || regex(str(?labelOrigin), '^PRKC*$') || regex(str(?labelOrigin), '^PRKDC$') || regex(str(?labelOrigin), '^Proteasome$') || regex(str(?labelOrigin), '^PTK2*$') || regex(str(?labelOrigin), '^RAF1$') || regex(str(?labelOrigin), '^RET$') || regex(str(?labelOrigin), '^RRM1$') || regex(str(?labelOrigin), '^SLC16A3$') || regex(str(?labelOrigin), '^SLC16A4$') || regex(str(?labelOrigin), '^SRC$') || regex(str(?labelOrigin), '^SYK$') || regex(str(?labelOrigin), '^TEK$') || regex(str(?labelOrigin), '^Thiamine$') || regex(str(?labelOrigin), '^TIE2$') || regex(str(?labelOrigin), '^TNFA$') || regex(str(?labelOrigin), '^TNFSF10$') || regex(str(?labelOrigin), '^TNKS$') || regex(str(?labelOrigin), '^TOP*$') || regex(str(?labelOrigin), '^TOP1$') || regex(str(?labelOrigin), '^TOP1MT$') || regex(str(?labelOrigin), '^TOP2*$') || regex(str(?labelOrigin), '^TTK$') || regex(str(?labelOrigin), '^TUBB$') || regex(str(?labelOrigin), '^TUBB1$') || regex(str(?labelOrigin), '^TYMS$') || regex(str(?labelOrigin), '^UBA3$') || regex(str(?labelOrigin), '^VEGFR2$') || regex(str(?labelOrigin), '^WEE1$') || regex(str(?labelOrigin), '^WNT*$') || regex(str(?labelOrigin), '^XIAP$') )  .\n" + 

							"FILTER (?targetDown != ?targetDown2) . \n"+
							"FILTER (?interactionDown != ?interactionDown2) . \n"+
							"FILTER (?downOne != ?targetDown) . \n"+
//							"FILTER (?targetDown != ?targetDown2) . \n"+
//							"FILTER (?interactionDown != ?interactionDown1) . \n"+
        	    			
							"FILTER (regex(str(?label3), '^EGFR$') || regex(str(?label3), '^CD19 antibody$') || regex(str(?label3), '^AKT1$') || regex(str(?label3), '^AKT2$') || regex(str(?label3), '^AKT*') || regex(str(?label3), '^PIK3C') || regex(str(?label3), '^SGK*') || regex(str(?label3), '^ALK$') || regex(str(?label3), '^IGF*R$') || regex(str(?label3), '^IGFR$') || regex(str(?label3), '^AR$') || regex(str(?label3), '^ATM$') || regex(str(?label3), '^ATR$') || regex(str(?label3), '^BCL2$') || regex(str(?label3), '^AKT3$') || regex(str(?label3), '^BCL2L1$') || regex(str(?label3), '^BCL2L2$') || regex(str(?label3), '^BIRC2$') || regex(str(?label3), '^BIRC3$') || regex(str(?label3), '^BIRC5$') || regex(str(?label3), '^BRAF$') || regex(str(?label3), '^BRAF_mut$') || regex(str(?label3), '^BRAF_V600E$') || regex(str(?label3), '^CHEK1$') || regex(str(?label3), '^CHK1$') || regex(str(?label3), '^CHUK$') || regex(str(?label3), '^cMET$') || regex(str(?label3), '^CMPK1$') || regex(str(?label3), '^CSNK2A1$') || regex(str(?label3), '^DNA$') || regex(str(?label3), '^DNMT1$') || regex(str(?label3), '^ERBB') || regex(str(?label3), '^ESR1$') || regex(str(?label3), '^FASN$') || regex(str(?label3), '^FGFR1$') || regex(str(?label3), '^FGFR2$') || regex(str(?label3), '^FGFR3$') || regex(str(?label3), '^FGFR4$') || regex(str(?label3), '^FLT1$') || regex(str(?label3), '^FLT4$') || regex(str(?label3), '^FNTA$') || regex(str(?label3), '^FNTB$') || regex(str(?label3), '^Gamma secretase$') || regex(str(?label3), '^HDAC*') || regex(str(?label3), '^HDAC1$') || regex(str(?label3), '^HDAC3$') || regex(str(?label3), '^HSP90AA1$') || regex(str(?label3), '^IGF*R$') || regex(str(?label3), '^IKBK*') || regex(str(?label3), '^JAK1$') || regex(str(?label3), '^JAK2$') || regex(str(?label3), '^KDR$') || regex(str(?label3), '^KIF11$') || regex(str(?label3), '^MAP2$') || regex(str(?label3), '^MAP2K*$') || regex(str(?label3), '^MAP2K1$') || regex(str(?label3), '^MAP2K2$') || regex(str(?label3), '^Methylation$') || regex(str(?label3), '^microtubule$') || regex(str(?label3), '^MTOR$') || regex(str(?label3), '^NAE1$') || regex(str(?label3), '^NAE2$') || regex(str(?label3), '^MAP4$') || regex(str(?label3), '^NIAP$') || regex(str(?label3), '^MAPT$') || regex(str(?label3), '^MET$') || regex(str(?label3), '^NR1I2$') || regex(str(?label3), '^PARP1$') || regex(str(?label3), '^PARP6$') || regex(str(?label3), '^PDGFRA$') || regex(str(?label3), '^PIK3C*$') || regex(str(?label3), '^PIK3CA$') || regex(str(?label3), '^PIK3CB$') || regex(str(?label3), '^PIK3CD$') || regex(str(?label3), '^PIM1$') || regex(str(?label3), '^PIP5K1*$') || regex(str(?label3), '^PRKC*$') || regex(str(?label3), '^PRKDC$') || regex(str(?label3), '^Proteasome$') || regex(str(?label3), '^PTK2*$') || regex(str(?label3), '^RAF1$') || regex(str(?label3), '^RET$') || regex(str(?label3), '^RRM1$') || regex(str(?label3), '^SLC16A3$') || regex(str(?label3), '^SLC16A4$') || regex(str(?label3), '^SRC$') || regex(str(?label3), '^SYK$') || regex(str(?label3), '^TEK$') || regex(str(?label3), '^Thiamine$') || regex(str(?label3), '^TIE2$') || regex(str(?label3), '^TNFA$') || regex(str(?label3), '^TNFSF10$') || regex(str(?label3), '^TNKS$') || regex(str(?label3), '^TOP*$') || regex(str(?label3), '^TOP1$') || regex(str(?label3), '^TOP1MT$') || regex(str(?label3), '^TOP2*$') || regex(str(?label3), '^TTK$') || regex(str(?label3), '^TUBB$') || regex(str(?label3), '^TUBB1$') || regex(str(?label3), '^TYMS$') || regex(str(?label3), '^UBA3$') || regex(str(?label3), '^VEGFR2$') || regex(str(?label3), '^WEE1$') || regex(str(?label3), '^WNT*$') || regex(str(?label3), '^XIAP$') )  .\n" + 

        	    			"} ";
            
        	//create and execute uniprot query using jena libraries
            Query secondUpDownQuery = QueryFactory.create(s3); //s2 = the query above
//          QueryExecution qExe = QueryExecutionFactory.sparqlService( 
//            "http://sparql.uniprot.org/sparql", query );
            QueryExecution qExeSecondUpDown = QueryExecutionFactory.sparqlService( 
            		"http://sparql.wikipathways.org/", secondUpDownQuery );

            ResultSet secondUpDownResults = qExeSecondUpDown.execSelect();
            
            ArrayList<String> arThirdDown = new ArrayList<String>();
            ArrayList<String> arFourthDown = new ArrayList<String>();
            
            String fourthDown = "";
            String thirdDown = "";
            String label3 = "";
            String label4 = "";
            
            while (secondUpDownResults.hasNext()){
            	
            	QuerySolution secondUpDownSolution = secondUpDownResults.next();
            	if (secondUpDownSolution.get("targetDown") == null){
            		thirdDown = "---------------------";
//                    arThirdDown.add(thirdDown);	
                    
                    if (secondUpDownSolution.get("targetDown2") == null) {
                    	fourthDown = "--------------------";
//                		arFourthDown.add(fourthDown);
                		label4 = "--------------------";
                    }
                    else {
                    	fourthDown = secondUpDownSolution.get("targetDown2").toString();
                    	label4 = secondUpDownSolution.get("label3").toString();
//                    	arFourthDown.add(fourthDown);
                    }
            		
            	}
//                	original = firstUpDownSolution.get("geneProduct").toString();
//                	bw.write(original + "\t" + firstUp + "\t" + firstDown + "\n");
            	else if	(secondUpDownSolution.get("targetDown2") == null){
            		fourthDown = "--------------------";
//            		arFourthDown.add(fourthDown);
            		label4 = "--------------------";
            		/*thirdDown = secondUpDownSolution.get("targetDown").toString();
                	label3 = secondUpDownSolution.get("label2").toString();
                	arThirdDown.add(thirdDown);*/
            		
            		if (secondUpDownSolution.get("targetDown") == null) {
                    	thirdDown = "--------------------";
//                		arThirdDown.add(thirdDown);
                		label3 = "--------------------";
                    }
                    else {
                    	thirdDown = secondUpDownSolution.get("targetDown").toString();
                    	label3 = secondUpDownSolution.get("label1").toString();
//                    	arThirdDown.add(thirdDown);
                    }

            	}
            	else{
            		if(secondUpDownSolution.get("targetDown").toString().matches("(?i).*chebi.*")||
            				secondUpDownSolution.get("targetDown").toString().matches("(?i).*wikipathways.*")
            				||
            				secondUpDownSolution.get("targetDown").toString().matches("(?i).*hmdb.*")
            				||
            				secondUpDownSolution.get("targetDown").toString().matches("(?i).*pubchem.*")){
            		}
            		else{
            			thirdDown = secondUpDownSolution.get("targetDown").toString();
            			label3 = secondUpDownSolution.get("label1").toString();
            		}
	            	
            		if(secondUpDownSolution.get("targetDown2").toString().matches("(?i).*chebi.*")||
            				secondUpDownSolution.get("targetDown2").toString().matches("(?i).*wikipathways.*")
            				||
            				secondUpDownSolution.get("targetDown2").toString().matches("(?i).*hmdb.*")
            				||
            				secondUpDownSolution.get("targetDown2").toString().matches("(?i).*pubchem.*")){
            		}
            		else{
            			fourthDown = secondUpDownSolution.get("targetDown2").toString();
    	            	label4 = secondUpDownSolution.get("label3").toString();
            		}
	            	
//	            	System.out.println(fourthDown);
	            	arFourthDown.add(fourthDown);
	            	arThirdDown.add(thirdDown);
	            	
	            	
	            	//bw.append(uniprotTarget+"\t"+targetChEMBL+"\n");
	            	//bw.append(targetChEMBL + "\t" + uniprotTarget + "\t" + wpSource +
					//		"\t" + wpTarget + "\t" + wpSecondSource + "\t" + wp2uniprot +
	                //    	"\t" + uniprot2ChEMBL + "\n");
	            	
            	}
//            	System.out.println(thirdDown);
            	bw.append(DownTwo.getOriginal() + "\t" + DownTwo.getFirstDown()+ "\t" + DownTwo.getLabel1() + "\t" + DownTwo.getSecondDown()+ "\t" + 
            			DownTwo.getLabel2() + "\t" + thirdDown + "\t" + label3 + "\t" + fourthDown + "\t" + label4 +"\n");
            }
            
                    
        }
        bw.close();//}
        bw1.close();
                
//            System.out.print(arUniprot);
            
        
            }  }
            

        
        

    
	

