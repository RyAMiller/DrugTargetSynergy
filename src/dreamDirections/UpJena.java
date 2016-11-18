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

public class UpJena {

public static void main( String[] args ) throws IOException {
        
    	//setting the output file source
    	File outFile = new File("./DreamOutFileUpTest.txt");
    	File firstOut = new File("./firstOutUpTest.txt");
    	
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
		bw.write("OriginalPoint\tfirstUp\tlabel\tSecondUp\tlabel\tThirdUp\t"
				+ "label\tFourthUp\tlabel\n");
		bw1.write("OriginalPoint\tfirstUp\tlabel\tsecondUp\tlabel\tthirdUp\n");
		
    	//first query to ChEMBL to get ChEMBL target IDs for FDA approved drugs
    	String s2 = 
    			"PREFIX wp:      <http://vocabularies.wikipathways.org/wp#>\n"+
    			"PREFIX rdfs:    <http://www.w3.org/2000/01/rdf-schema#>\n"+
    			"PREFIX dcterms: <http://purl.org/dc/terms/>\n"+
//    			"PREFIX xsd:     <http://www.w3.org/2001/XMLSchema#>\n"+
    			"PREFIX rdf:     <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"+
    			"PREFIX dc:      <http://purl.org/dc/elements/1.1/>\n"+
    			
    			"SELECT DISTINCT ?geneProduct ?sourceUp ?label2 ?sourceUp2 ?label3 \n"+
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
    			"  ?targetUp dc:identifier ?geneProduct .\n"+

    			"  ?interactionUp rdf:type wp:DirectedInteraction .\n"+
    			"  ?interactionUp wp:source ?sourceUp .\n"+
    			"  ?interactionUp wp:target ?targetUp .\n"+

    			"  ?sourceUp rdfs:label ?label2 .\n"+
    			"  ?targetUp rdfs:label ?labelOrigin .\n"+
//    			"}\n"+

    			"OPTIONAL\n"+
    			"{\n"+
    			"  ?targetUp1 dc:identifier ?sourceUp .\n"+

				"  ?interactionUp2 rdf:type wp:DirectedInteraction .\n"+
				"  ?interactionUp2 wp:source ?sourceUp2 .\n"+
				"  ?interactionUp2 wp:target ?targetUp1 .\n"+

//				"  ?targetUp rdfs:label ?label2 .\n"+
				"  ?sourceUp2 rdfs:label ?label3 .\n"+
    			"}\n"+
    				    
    			"FILTER (regex(str(?labelOrigin), '^EGFR$') || regex(str(?labelOrigin), '^CD19 antibody$') || regex(str(?labelOrigin), '^AKT1$') || regex(str(?labelOrigin), '^AKT2$') || regex(str(?labelOrigin), '^AKT*') || regex(str(?labelOrigin), '^PIK3C') || regex(str(?labelOrigin), '^SGK*') || regex(str(?labelOrigin), '^ALK$') || regex(str(?labelOrigin), '^IGF*R$') || regex(str(?labelOrigin), '^IGFR$') || regex(str(?labelOrigin), '^AR$') || regex(str(?labelOrigin), '^ATM$') || regex(str(?labelOrigin), '^ATR$') || regex(str(?labelOrigin), '^BCL2$') || regex(str(?labelOrigin), '^AKT3$') || regex(str(?labelOrigin), '^BCL2L1$') || regex(str(?labelOrigin), '^BCL2L2$') || regex(str(?labelOrigin), '^BIRC2$') || regex(str(?labelOrigin), '^BIRC3$') || regex(str(?labelOrigin), '^BIRC5$') || regex(str(?labelOrigin), '^BRAF$') || regex(str(?labelOrigin), '^BRAF_mut$') || regex(str(?labelOrigin), '^BRAF_V600E$') || regex(str(?labelOrigin), '^CHEK1$') || regex(str(?labelOrigin), '^CHK1$') || regex(str(?labelOrigin), '^CHUK$') || regex(str(?labelOrigin), '^cMET$') || regex(str(?labelOrigin), '^CMPK1$') || regex(str(?labelOrigin), '^CSNK2A1$') || regex(str(?labelOrigin), '^DNA$') || regex(str(?labelOrigin), '^DNMT1$') || regex(str(?labelOrigin), '^ERBB') || regex(str(?labelOrigin), '^ESR1$') || regex(str(?labelOrigin), '^FASN$') || regex(str(?labelOrigin), '^FGFR1$') || regex(str(?labelOrigin), '^FGFR2$') || regex(str(?labelOrigin), '^FGFR3$') || regex(str(?labelOrigin), '^FGFR4$') || regex(str(?labelOrigin), '^FLT1$') || regex(str(?labelOrigin), '^FLT4$') || regex(str(?labelOrigin), '^FNTA$') || regex(str(?labelOrigin), '^FNTB$') || regex(str(?labelOrigin), '^Gamma secretase$') || regex(str(?labelOrigin), '^HDAC*') || regex(str(?labelOrigin), '^HDAC1$') || regex(str(?labelOrigin), '^HDAC3$') || regex(str(?labelOrigin), '^HSP90AA1$') || regex(str(?labelOrigin), '^IGF*R$') || regex(str(?labelOrigin), '^IKBK*') || regex(str(?labelOrigin), '^JAK1$') || regex(str(?labelOrigin), '^JAK2$') || regex(str(?labelOrigin), '^KDR$') || regex(str(?labelOrigin), '^KIF11$') || regex(str(?labelOrigin), '^MAP2$') || regex(str(?labelOrigin), '^MAP2K*$') || regex(str(?labelOrigin), '^MAP2K1$') || regex(str(?labelOrigin), '^MAP2K2$') || regex(str(?labelOrigin), '^Methylation$') || regex(str(?labelOrigin), '^microtubule$') || regex(str(?labelOrigin), '^MTOR$') || regex(str(?labelOrigin), '^NAE1$') || regex(str(?labelOrigin), '^NAE2$') || regex(str(?labelOrigin), '^MAP4$') || regex(str(?labelOrigin), '^NIAP$') || regex(str(?labelOrigin), '^MAPT$') || regex(str(?labelOrigin), '^MET$') || regex(str(?labelOrigin), '^NR1I2$') || regex(str(?labelOrigin), '^PARP1$') || regex(str(?labelOrigin), '^PARP6$') || regex(str(?labelOrigin), '^PDGFRA$') || regex(str(?labelOrigin), '^PIK3C*$') || regex(str(?labelOrigin), '^PIK3CA$') || regex(str(?labelOrigin), '^PIK3CB$') || regex(str(?labelOrigin), '^PIK3CD$') || regex(str(?labelOrigin), '^PIM1$') || regex(str(?labelOrigin), '^PIP5K1*$') || regex(str(?labelOrigin), '^PRKC*$') || regex(str(?labelOrigin), '^PRKDC$') || regex(str(?labelOrigin), '^Proteasome$') || regex(str(?labelOrigin), '^PTK2*$') || regex(str(?labelOrigin), '^RAF1$') || regex(str(?labelOrigin), '^RET$') || regex(str(?labelOrigin), '^RRM1$') || regex(str(?labelOrigin), '^SLC16A3$') || regex(str(?labelOrigin), '^SLC16A4$') || regex(str(?labelOrigin), '^SRC$') || regex(str(?labelOrigin), '^SYK$') || regex(str(?labelOrigin), '^TEK$') || regex(str(?labelOrigin), '^Thiamine$') || regex(str(?labelOrigin), '^TIE2$') || regex(str(?labelOrigin), '^TNFA$') || regex(str(?labelOrigin), '^TNFSF10$') || regex(str(?labelOrigin), '^TNKS$') || regex(str(?labelOrigin), '^TOP*$') || regex(str(?labelOrigin), '^TOP1$') || regex(str(?labelOrigin), '^TOP1MT$') || regex(str(?labelOrigin), '^TOP2*$') || regex(str(?labelOrigin), '^TTK$') || regex(str(?labelOrigin), '^TUBB$') || regex(str(?labelOrigin), '^TUBB1$') || regex(str(?labelOrigin), '^TYMS$') || regex(str(?labelOrigin), '^UBA3$') || regex(str(?labelOrigin), '^VEGFR2$') || regex(str(?labelOrigin), '^WEE1$') || regex(str(?labelOrigin), '^WNT*$') || regex(str(?labelOrigin), '^XIAP$') )  .\n" + 
    			"FILTER (?geneProduct != ?sourceUp2) . \n"+
    			"FILTER (?sourceUp != ?sourceUp2) . \n"+
    			"FILTER (?geneProduct != ?sourceUp)"+
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
        
        
        ArrayList<String> arFirstUp = new ArrayList<String>();
        Set<FirstQueryUpResult> arSecondUp = new HashSet<FirstQueryUpResult>();
        
        String firstUp = "";
        String label1 = "";
        String secondUp = "";
        String label2 = "";
        String original = "";
        
        
        //loops through the ChemblResults set results until end of file
        //assigns ChEMBL targets to array and writes to output file
        while (firstUpDownResults.hasNext()){
        	FirstQueryUpResult fqur =  new FirstQueryUpResult("");
        	QuerySolution firstUpDownSolution = firstUpDownResults.next();

        	if (firstUpDownSolution.get("sourceUp") == null){
        		firstUp = "xxxxxxxxxxxxxxxxxxxxx";
        		             
//                arFirstUp.add(firstUp);	
                label1 = "xxxxxxxxxxxxxxxxxxxxx";
                
                if (firstUpDownSolution.get("sourceUp2") == null) {
                	secondUp = "xxxxxxxxxxxxxxxxxxxxx";
//            		arSecondUp.add(secondUp);
            		label2 = "xxxxxxxxxxxxxxxxxxxxx";
                }
                else {
                	secondUp = firstUpDownSolution.get("sourceUp2").toString();
                	label2 = firstUpDownSolution.get("label3").toString();
                	fqur = new FirstQueryUpResult(secondUp);
                	arSecondUp.add(fqur);
                }
        		
        	}
//            	original = firstUpDownSolution.get("geneProduct").toString();
//            	bw.write(original + "\t" + firstUp + "\t" + firstUp + "\n");
        	else if	(firstUpDownSolution.get("sourceUp2") == null){
        		secondUp = "xxxxxxxxxxxxxxxxxxxxx";
//        		arSecondUp.add(secondUp);
        		label2 = "xxxxxxxxxxxxxxxxxxxxx";
        		if (firstUpDownSolution.get("sourceUp") == null) {
        			firstUp = "xxxxxxxxxxxxxxxxxxxxx";
//                    arFirstUp.add(firstUp);	
                    label1 = "xxxxxxxxxxxxxxxxxxxxx";
        		}
        		else {
        			firstUp = firstUpDownSolution.get("sourceUp").toString();
                	label1 = firstUpDownSolution.get("label2").toString();
//                	arSecondUp.add(secondUp);
        		}
        	}
        	else{
        		if(firstUpDownSolution.get("sourceUp").toString().matches("(?i).*chebi.*")||
        				firstUpDownSolution.get("sourceUp").toString().matches("(?i).*wikipathways.*")
        				||
        				firstUpDownSolution.get("sourceUp").toString().matches("(?i).*hmdb.*")
        				||
        				firstUpDownSolution.get("sourceUp").toString().matches("(?i).*pubchem.*"))
        		{}
        		else{
        			firstUp = firstUpDownSolution.get("sourceUp").toString();
        			label1 = firstUpDownSolution.get("label2").toString();
//        			arFirstUp.add(firstUp);
        		}
        		if(firstUpDownSolution.get("sourceUp2").toString().matches("(?i).*chebi.*")||
        				firstUpDownSolution.get("sourceUp2").toString().matches("(?i).*wikipathways.*")
        				||
        				firstUpDownSolution.get("sourceUp2").toString().matches("(?i).*hmdb.*")
        				||
        				firstUpDownSolution.get("sourceUp2").toString().matches("(?i).*pubchem.*"))
        		{}
        		else{
        			secondUp = firstUpDownSolution.get("sourceUp2").toString();
        			label2 = firstUpDownSolution.get("label3").toString();
        			fqur = new FirstQueryUpResult(secondUp);
        			arSecondUp.add(fqur);
        		}
        		
//        		arSecondUp.add(secondUp);
//        		arFirstUp.add(firstUp);
        		
//        		original = firstUpDownSolution.get("geneProduct").toString();
//        		bw.write(original + "\t" + firstUp + "\t" + firstUp + "\n");
        		
        		
        	}
        	original = firstUpDownSolution.get("geneProduct").toString();
//        	System.out.println(firstUp);
        	
        	bw1.write(original + "\t" + firstUp + "\t" + label1 + "\t" +  secondUp + "\t"
    				+ label2 + "\n");
//        	bw.write(original + "\t" + firstUp + "\t" + label1 + "\t" + secondUp + "\t" + label2 + "\n");
        	//bw.write(ChEMBLTarget+"\n");
        	
//        	ChEMBLTarget = ChemblSolution.getResource("ChEMBLTarget").getURI();
//        	toto=solution.get("ChEMBLTarget").toString();
        }
        //check to see what the output of ChEMBLTarget variable is
//        System.out.println(firstUp);
        
		
        System.out.println("Done");
        
        
        
        //loop for the elements in the ChEMBL array from the previous query 
        for (FirstQueryUpResult UpTwo : arSecondUp){
        	System.out.println(UpTwo);
        	//query that takes ChEMBL ID type to a Uniprot ID (that can be used in WPs)
        	String s3 = 
        			"PREFIX wp:      <http://vocabularies.wikipathways.org/wp#>\n"+
        	    			"PREFIX rdfs:    <http://www.w3.org/2000/01/rdf-schema#>\n"+
        	    			"PREFIX dcterms: <http://purl.org/dc/terms/>\n"+
        	    			"PREFIX dc:      <http://purl.org/dc/elements/1.1/>\n"+
        	    			"PREFIX rdf:     <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"+

        	    			"SELECT DISTINCT ?UpOne ?sourceUp ?label1 ?sourceUp2 ?label3 \n"+
        	    			"WHERE {\n"+
        	    			

        	    			"  <"+ UpTwo +"> dc:identifier ?UpOne . \n"+  
        	    			"  ?UpOne rdf:type wp:DataNode . \n"+
        	    			"  ?UpOne dcterms:isPartOf ?pathway . \n"+
        	    			"  ?pathway rdf:type wp:Pathway . \n"+
        	    			"  ?pathway wp:organism  <http://purl.obolibrary.org/obo/NCBITaxon_9606> . \n"+

        	    			"  ?interactionUp rdf:type wp:DirectedInteraction . \n"+
        	    			"  ?interactionUp wp:source ?UpOne . \n"+
        	    			"  ?interactionUp wp:target ?sourceUp . \n"+

        	    			"  ?UpOne rdfs:label ?labelOrigin . \n"+
        	    			"  ?sourceUp rdfs:label ?label1 . \n"+

        	    			
							"OPTIONAL\n"+
							"{\n"+
							"  ?sourceUp dc:identifier ?sourceUp1 .\n"+
							
							"  ?interactionUp2 rdf:type wp:DirectedInteraction .\n"+
							"  ?interactionUp2 wp:source ?sourceUp2 .\n"+
							"  ?interactionUp2 wp:target ?sourceUp1 .\n"+
							
							//"  ?sourceUp rdfs:label ?label2 .\n"+
							"  ?sourceUp2 rdfs:label ?label3 .\n"+
							"}\n"+
								    
//"FILTER (regex(str(?labelOrigin), '^EGFR$') || regex(str(?labelOrigin), '^CD19 antibody$') || regex(str(?labelOrigin), '^AKT1$') || regex(str(?labelOrigin), '^AKT2$') || regex(str(?labelOrigin), '^AKT*') || regex(str(?labelOrigin), '^PIK3C') || regex(str(?labelOrigin), '^SGK*') || regex(str(?labelOrigin), '^ALK$') || regex(str(?labelOrigin), '^IGF*R$') || regex(str(?labelOrigin), '^IGFR$') || regex(str(?labelOrigin), '^AR$') || regex(str(?labelOrigin), '^ATM$') || regex(str(?labelOrigin), '^ATR$') || regex(str(?labelOrigin), '^BCL2$') || regex(str(?labelOrigin), '^AKT3$') || regex(str(?labelOrigin), '^BCL2L1$') || regex(str(?labelOrigin), '^BCL2L2$') || regex(str(?labelOrigin), '^BIRC2$') || regex(str(?labelOrigin), '^BIRC3$') || regex(str(?labelOrigin), '^BIRC5$') || regex(str(?labelOrigin), '^BRAF$') || regex(str(?labelOrigin), '^BRAF_mut$') || regex(str(?labelOrigin), '^BRAF_V600E$') || regex(str(?labelOrigin), '^CHEK1$') || regex(str(?labelOrigin), '^CHK1$') || regex(str(?labelOrigin), '^CHUK$') || regex(str(?labelOrigin), '^cMET$') || regex(str(?labelOrigin), '^CMPK1$') || regex(str(?labelOrigin), '^CSNK2A1$') || regex(str(?labelOrigin), '^DNA$') || regex(str(?labelOrigin), '^DNMT1$') || regex(str(?labelOrigin), '^ERBB') || regex(str(?labelOrigin), '^ESR1$') || regex(str(?labelOrigin), '^FASN$') || regex(str(?labelOrigin), '^FGFR1$') || regex(str(?labelOrigin), '^FGFR2$') || regex(str(?labelOrigin), '^FGFR3$') || regex(str(?labelOrigin), '^FGFR4$') || regex(str(?labelOrigin), '^FLT1$') || regex(str(?labelOrigin), '^FLT4$') || regex(str(?labelOrigin), '^FNTA$') || regex(str(?labelOrigin), '^FNTB$') || regex(str(?labelOrigin), '^Gamma secretase$') || regex(str(?labelOrigin), '^HDAC*') || regex(str(?labelOrigin), '^HDAC1$') || regex(str(?labelOrigin), '^HDAC3$') || regex(str(?labelOrigin), '^HSP90AA1$') || regex(str(?labelOrigin), '^IGF*R$') || regex(str(?labelOrigin), '^IKBK*') || regex(str(?labelOrigin), '^JAK1$') || regex(str(?labelOrigin), '^JAK2$') || regex(str(?labelOrigin), '^KDR$') || regex(str(?labelOrigin), '^KIF11$') || regex(str(?labelOrigin), '^MAP2$') || regex(str(?labelOrigin), '^MAP2K*$') || regex(str(?labelOrigin), '^MAP2K1$') || regex(str(?labelOrigin), '^MAP2K2$') || regex(str(?labelOrigin), '^Methylation$') || regex(str(?labelOrigin), '^microtubule$') || regex(str(?labelOrigin), '^MTOR$') || regex(str(?labelOrigin), '^NAE1$') || regex(str(?labelOrigin), '^NAE2$') || regex(str(?labelOrigin), '^MAP4$') || regex(str(?labelOrigin), '^NIAP$') || regex(str(?labelOrigin), '^MAPT$') || regex(str(?labelOrigin), '^MET$') || regex(str(?labelOrigin), '^NR1I2$') || regex(str(?labelOrigin), '^PARP1$') || regex(str(?labelOrigin), '^PARP6$') || regex(str(?labelOrigin), '^PDGFRA$') || regex(str(?labelOrigin), '^PIK3C*$') || regex(str(?labelOrigin), '^PIK3CA$') || regex(str(?labelOrigin), '^PIK3CB$') || regex(str(?labelOrigin), '^PIK3CD$') || regex(str(?labelOrigin), '^PIM1$') || regex(str(?labelOrigin), '^PIP5K1*$') || regex(str(?labelOrigin), '^PRKC*$') || regex(str(?labelOrigin), '^PRKDC$') || regex(str(?labelOrigin), '^Proteasome$') || regex(str(?labelOrigin), '^PTK2*$') || regex(str(?labelOrigin), '^RAF1$') || regex(str(?labelOrigin), '^RET$') || regex(str(?labelOrigin), '^RRM1$') || regex(str(?labelOrigin), '^SLC16A3$') || regex(str(?labelOrigin), '^SLC16A4$') || regex(str(?labelOrigin), '^SRC$') || regex(str(?labelOrigin), '^SYK$') || regex(str(?labelOrigin), '^TEK$') || regex(str(?labelOrigin), '^Thiamine$') || regex(str(?labelOrigin), '^TIE2$') || regex(str(?labelOrigin), '^TNFA$') || regex(str(?labelOrigin), '^TNFSF10$') || regex(str(?labelOrigin), '^TNKS$') || regex(str(?labelOrigin), '^TOP*$') || regex(str(?labelOrigin), '^TOP1$') || regex(str(?labelOrigin), '^TOP1MT$') || regex(str(?labelOrigin), '^TOP2*$') || regex(str(?labelOrigin), '^TTK$') || regex(str(?labelOrigin), '^TUBB$') || regex(str(?labelOrigin), '^TUBB1$') || regex(str(?labelOrigin), '^TYMS$') || regex(str(?labelOrigin), '^UBA3$') || regex(str(?labelOrigin), '^VEGFR2$') || regex(str(?labelOrigin), '^WEE1$') || regex(str(?labelOrigin), '^WNT*$') || regex(str(?labelOrigin), '^XIAP$') )  .\n" + 

							"FILTER (?sourceUp != ?sourceUp2) . \n"+
							"FILTER (?interactionUp != ?interactionUp2) . \n"+
							"FILTER (?UpOne != ?sourceUp) . \n"+
//							"FILTER (?sourceUp != ?sourceUp2) . \n"+
//							"FILTER (?interactionUp != ?interactionUp1) . \n"+
        	    			
							"FILTER (regex(str(?label3), '^EGFR$') || regex(str(?label3), '^CD19 antibody$') || regex(str(?label3), '^AKT1$') || regex(str(?label3), '^AKT2$') || regex(str(?label3), '^AKT*') || regex(str(?label3), '^PIK3C') || regex(str(?label3), '^SGK*') || regex(str(?label3), '^ALK$') || regex(str(?label3), '^IGF*R$') || regex(str(?label3), '^IGFR$') || regex(str(?label3), '^AR$') || regex(str(?label3), '^ATM$') || regex(str(?label3), '^ATR$') || regex(str(?label3), '^BCL2$') || regex(str(?label3), '^AKT3$') || regex(str(?label3), '^BCL2L1$') || regex(str(?label3), '^BCL2L2$') || regex(str(?label3), '^BIRC2$') || regex(str(?label3), '^BIRC3$') || regex(str(?label3), '^BIRC5$') || regex(str(?label3), '^BRAF$') || regex(str(?label3), '^BRAF_mut$') || regex(str(?label3), '^BRAF_V600E$') || regex(str(?label3), '^CHEK1$') || regex(str(?label3), '^CHK1$') || regex(str(?label3), '^CHUK$') || regex(str(?label3), '^cMET$') || regex(str(?label3), '^CMPK1$') || regex(str(?label3), '^CSNK2A1$') || regex(str(?label3), '^DNA$') || regex(str(?label3), '^DNMT1$') || regex(str(?label3), '^ERBB') || regex(str(?label3), '^ESR1$') || regex(str(?label3), '^FASN$') || regex(str(?label3), '^FGFR1$') || regex(str(?label3), '^FGFR2$') || regex(str(?label3), '^FGFR3$') || regex(str(?label3), '^FGFR4$') || regex(str(?label3), '^FLT1$') || regex(str(?label3), '^FLT4$') || regex(str(?label3), '^FNTA$') || regex(str(?label3), '^FNTB$') || regex(str(?label3), '^Gamma secretase$') || regex(str(?label3), '^HDAC*') || regex(str(?label3), '^HDAC1$') || regex(str(?label3), '^HDAC3$') || regex(str(?label3), '^HSP90AA1$') || regex(str(?label3), '^IGF*R$') || regex(str(?label3), '^IKBK*') || regex(str(?label3), '^JAK1$') || regex(str(?label3), '^JAK2$') || regex(str(?label3), '^KDR$') || regex(str(?label3), '^KIF11$') || regex(str(?label3), '^MAP2$') || regex(str(?label3), '^MAP2K*$') || regex(str(?label3), '^MAP2K1$') || regex(str(?label3), '^MAP2K2$') || regex(str(?label3), '^Methylation$') || regex(str(?label3), '^microtubule$') || regex(str(?label3), '^MTOR$') || regex(str(?label3), '^NAE1$') || regex(str(?label3), '^NAE2$') || regex(str(?label3), '^MAP4$') || regex(str(?label3), '^NIAP$') || regex(str(?label3), '^MAPT$') || regex(str(?label3), '^MET$') || regex(str(?label3), '^NR1I2$') || regex(str(?label3), '^PARP1$') || regex(str(?label3), '^PARP6$') || regex(str(?label3), '^PDGFRA$') || regex(str(?label3), '^PIK3C*$') || regex(str(?label3), '^PIK3CA$') || regex(str(?label3), '^PIK3CB$') || regex(str(?label3), '^PIK3CD$') || regex(str(?label3), '^PIM1$') || regex(str(?label3), '^PIP5K1*$') || regex(str(?label3), '^PRKC*$') || regex(str(?label3), '^PRKDC$') || regex(str(?label3), '^Proteasome$') || regex(str(?label3), '^PTK2*$') || regex(str(?label3), '^RAF1$') || regex(str(?label3), '^RET$') || regex(str(?label3), '^RRM1$') || regex(str(?label3), '^SLC16A3$') || regex(str(?label3), '^SLC16A4$') || regex(str(?label3), '^SRC$') || regex(str(?label3), '^SYK$') || regex(str(?label3), '^TEK$') || regex(str(?label3), '^Thiamine$') || regex(str(?label3), '^TIE2$') || regex(str(?label3), '^TNFA$') || regex(str(?label3), '^TNFSF10$') || regex(str(?label3), '^TNKS$') || regex(str(?label3), '^TOP*$') || regex(str(?label3), '^TOP1$') || regex(str(?label3), '^TOP1MT$') || regex(str(?label3), '^TOP2*$') || regex(str(?label3), '^TTK$') || regex(str(?label3), '^TUBB$') || regex(str(?label3), '^TUBB1$') || regex(str(?label3), '^TYMS$') || regex(str(?label3), '^UBA3$') || regex(str(?label3), '^VEGFR2$') || regex(str(?label3), '^WEE1$') || regex(str(?label3), '^WNT*$') || regex(str(?label3), '^XIAP$') )  .\n" + 

        	    			"} ";
            
        	//create and execute uniprot query using jena libraries
            Query secondUpDownQuery = QueryFactory.create(s3); //s2 = the query above
//          QueryExecution qExe = QueryExecutionFactory.sparqlService( 
//            "http://sparql.uniprot.org/sparql", query );
            QueryExecution qExeSecondUpDown = QueryExecutionFactory.sparqlService( 
            		"http://sparql.wikipathways.org/", secondUpDownQuery );

            ResultSet secondUpDownResults = qExeSecondUpDown.execSelect();
            
            ArrayList<String> arThirdUp = new ArrayList<String>();
            ArrayList<String> arFourthUp = new ArrayList<String>();
            
            String fourthUp = "";
            String thirdUp = "";
            String label3 = "";
            String label4 = "";
            
            while (secondUpDownResults.hasNext()){
            	
            	QuerySolution secondUpDownSolution = secondUpDownResults.next();
            	if (secondUpDownSolution.get("sourceUp") == null){
            		thirdUp = "---------------------";
//                    arThirdUp.add(thirdUp);	
                    
                    if (secondUpDownSolution.get("sourceUp2") == null) {
                    	fourthUp = "--------------------";
//                		arFourthUp.add(fourthUp);
                		label4 = "--------------------";
                    }
                    else {
                    	fourthUp = secondUpDownSolution.get("sourceUp2").toString();
                    	label4 = secondUpDownSolution.get("label3").toString();
//                    	arFourthUp.add(fourthUp);
                    }
            		
            	}
//                	original = firstUpDownSolution.get("geneProduct").toString();
//                	bw.write(original + "\t" + firstUp + "\t" + firstUp + "\n");
            	else if	(secondUpDownSolution.get("sourceUp2") == null){
            		fourthUp = "--------------------";
//            		arFourthUp.add(fourthUp);
            		label4 = "--------------------";
            		/*thirdUp = secondUpDownSolution.get("sourceUp").toString();
                	label3 = secondUpDownSolution.get("label2").toString();
                	arThirdUp.add(thirdUp);*/
            		
            		if (secondUpDownSolution.get("sourceUp") == null) {
                    	thirdUp = "--------------------";
//                		arThirdUp.add(thirdUp);
                		label3 = "--------------------";
                    }
                    else {
                    	thirdUp = secondUpDownSolution.get("sourceUp").toString();
                    	label3 = secondUpDownSolution.get("label1").toString();
//                    	arThirdUp.add(thirdUp);
                    }

            	}
            	else{
            		if(secondUpDownSolution.get("sourceUp").toString().matches("(?i).*chebi.*")||
            				secondUpDownSolution.get("sourceUp").toString().matches("(?i).*wikipathways.*")
            				||
            				secondUpDownSolution.get("sourceUp").toString().matches("(?i).*hmdb.*")
            				||
            				secondUpDownSolution.get("sourceUp").toString().matches("(?i).*pubchem.*")){
            		}
            		else{
            			thirdUp = secondUpDownSolution.get("sourceUp").toString();
            			label3 = secondUpDownSolution.get("label1").toString();
            		}
	            	
            		if(secondUpDownSolution.get("sourceUp2").toString().matches("(?i).*chebi.*")||
            				secondUpDownSolution.get("sourceUp2").toString().matches("(?i).*wikipathways.*")
            				||
            				secondUpDownSolution.get("sourceUp2").toString().matches("(?i).*hmdb.*")
            				||
            				secondUpDownSolution.get("sourceUp2").toString().matches("(?i).*pubchem.*")){
            		}
            		else{
            			fourthUp = secondUpDownSolution.get("sourceUp2").toString();
    	            	label4 = secondUpDownSolution.get("label3").toString();
            		}
	            	
//	            	System.out.println(fourthUp);
	            	arFourthUp.add(fourthUp);
	            	arThirdUp.add(thirdUp);
	            	
	            	
	            	//bw.append(uniprotTarget+"\t"+targetChEMBL+"\n");
	            	//bw.append(targetChEMBL + "\t" + uniprotTarget + "\t" + wpSource +
					//		"\t" + wpTarget + "\t" + wpSecondSource + "\t" + wp2uniprot +
	                //    	"\t" + uniprot2ChEMBL + "\n");
	            	
            	}
            	System.out.println(thirdUp);
            	bw.append(UpTwo.getOriginal() + "\t" + UpTwo.getFirstUp()+ "\t" + UpTwo.getLabel1() + "\t" + UpTwo.getSecondUp()+ "\t" + 
            			UpTwo.getLabel2() + "\t" + label3 + "\t" + fourthUp + "/t" + label4 +"\n");
            }
            
                    
        }
        bw.close();//}
        bw1.close();
                
//            System.out.print(arUniprot);
            
        
            }
}
