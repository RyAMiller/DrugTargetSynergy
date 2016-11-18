package dreamDirections;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;

@SuppressWarnings("unused")

public class UpandDownJena {

public static void main( String[] args ) throws IOException {
        
    	//setting the output file source
    	File outFile = new File("./DreamOutFile1.txt");
    	
    	//checking to see if output file exists and if it does not, 
    	//create a new file with this name.
    	if (!outFile.exists()) {
			try {
				outFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    	
    	// create new filehandles to be written to
    	FileWriter fw = new FileWriter(outFile.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		
		// some column headers for output file
		bw.write("OriginalPoint\tfirstUp\tlabel\tSecondUp\tFirstDown\tlabel\t"
				+ "SecondDown\n");
		

    	
    	//first query to ChEMBL to get ChEMBL target IDs for FDA approved drugs
    	String s2 = 
    			"PREFIX wp:      <http://vocabularies.wikipathways.org/wp#>\n"+
    			"PREFIX rdfs:    <http://www.w3.org/2000/01/rdf-schema#>\n"+
    			"PREFIX dcterms: <http://purl.org/dc/terms/>\n"+
//    			"PREFIX xsd:     <http://www.w3.org/2001/XMLSchema#>\n"+
    			"PREFIX dc:      <http://purl.org/dc/elements/1.1/>\n"+
    			
    			"SELECT DISTINCT ?geneProduct ?targetDown ?label2 ?targetDown2 ?label3 \n"+
    			"WHERE {\n"+
    			"  ?geneProduct a wp:GeneProduct . \n"+
    			"  ?geneProduct rdfs:label ?labelOrigin .\n"+
    			"  ?geneProduct dcterms:isPartOf ?pathway .\n"+
    			"  ?pathway a wp:Pathway .\n"+
    			"  ?geneProduct dcterms:isPartOf ?anInteraction . \n"+
    			"  ?anInteraction a wp:DirectedInteraction . \n"+
    			"  ?pathway wp:organism  <http://purl.obolibrary.org/obo/NCBITaxon_9606> .\n"+

//    			" ?pathway dc:identifier ?pURI .\n"+

    			"OPTIONAL\n"+
    			"{\n"+
    			"  ?sourceDown dc:identifier ?geneProduct .\n"+

    			"  ?interactionDown a wp:DirectedInteraction .\n"+
    			"  ?interactionDown wp:source ?sourceDown .\n"+
    			"  ?interactionDown wp:target ?targetDown .\n"+

    			"  ?sourceDown rdfs:label ?labelOrigin .\n"+
    			"  ?targetDown rdfs:label ?label2 .\n"+
    			"}\n"+

    			"OPTIONAL\n"+
    			"{\n"+
//    			"  ?sourceDown dc:identifier ?targetDown .\n"+

				"  ?interactionDown2 a wp:DirectedInteraction .\n"+
				"  ?interactionDown2 wp:source ?sourceDown .\n"+
				"  ?interactionDown2 wp:target ?targetDown2 .\n"+

//				"  ?sourceDown rdfs:label ?label2 .\n"+
				"  ?targetDown2 rdfs:label ?label3 .\n"+
    			"}\n"+
    				    
    			"FILTER (regex(str(?labelOrigin), '^EGFR$') || regex(str(?labelOrigin), '^CD19 antibody$') || regex(str(?labelOrigin), '^AKT1$') || regex(str(?labelOrigin), '^AKT2$') || regex(str(?labelOrigin), '^AKT*') || regex(str(?labelOrigin), '^PIK3C') || regex(str(?labelOrigin), '^SGK*') || regex(str(?labelOrigin), '^ALK$') || regex(str(?labelOrigin), '^IGF*R$') || regex(str(?labelOrigin), '^IGFR$') || regex(str(?labelOrigin), '^AR$') || regex(str(?labelOrigin), '^ATM$') || regex(str(?labelOrigin), '^ATR$') || regex(str(?labelOrigin), '^BCL2$') || regex(str(?labelOrigin), '^AKT3$') || regex(str(?labelOrigin), '^BCL2L1$') || regex(str(?labelOrigin), '^BCL2L2$') || regex(str(?labelOrigin), '^BIRC2$') || regex(str(?labelOrigin), '^BIRC3$') || regex(str(?labelOrigin), '^BIRC5$') || regex(str(?labelOrigin), '^BRAF$') || regex(str(?labelOrigin), '^BRAF_mut$') || regex(str(?labelOrigin), '^BRAF_V600E$') || regex(str(?labelOrigin), '^CHEK1$') || regex(str(?labelOrigin), '^CHK1$') || regex(str(?labelOrigin), '^CHUK$') || regex(str(?labelOrigin), '^cMET$') || regex(str(?labelOrigin), '^CMPK1$') || regex(str(?labelOrigin), '^CSNK2A1$') || regex(str(?labelOrigin), '^DNA$') || regex(str(?labelOrigin), '^DNMT1$') || regex(str(?labelOrigin), '^ERBB') || regex(str(?labelOrigin), '^ESR1$') || regex(str(?labelOrigin), '^FASN$') || regex(str(?labelOrigin), '^FGFR1$') || regex(str(?labelOrigin), '^FGFR2$') || regex(str(?labelOrigin), '^FGFR3$') || regex(str(?labelOrigin), '^FGFR4$') || regex(str(?labelOrigin), '^FLT1$') || regex(str(?labelOrigin), '^FLT4$') || regex(str(?labelOrigin), '^FNTA$') || regex(str(?labelOrigin), '^FNTB$') || regex(str(?labelOrigin), '^Gamma secretase$') || regex(str(?labelOrigin), '^HDAC*') || regex(str(?labelOrigin), '^HDAC1$') || regex(str(?labelOrigin), '^HDAC3$') || regex(str(?labelOrigin), '^HSP90AA1$') || regex(str(?labelOrigin), '^IGF*R$') || regex(str(?labelOrigin), '^IKBK*') || regex(str(?labelOrigin), '^JAK1$') || regex(str(?labelOrigin), '^JAK2$') || regex(str(?labelOrigin), '^KDR$') || regex(str(?labelOrigin), '^KIF11$') || regex(str(?labelOrigin), '^MAP2$') || regex(str(?labelOrigin), '^MAP2K*$') || regex(str(?labelOrigin), '^MAP2K1$') || regex(str(?labelOrigin), '^MAP2K2$') || regex(str(?labelOrigin), '^Methylation$') || regex(str(?labelOrigin), '^microtubule$') || regex(str(?labelOrigin), '^MTOR$') || regex(str(?labelOrigin), '^NAE1$') || regex(str(?labelOrigin), '^NAE2$') || regex(str(?labelOrigin), '^MAP4$') || regex(str(?labelOrigin), '^NIAP$') || regex(str(?labelOrigin), '^MAPT$') || regex(str(?labelOrigin), '^MET$') || regex(str(?labelOrigin), '^NR1I2$') || regex(str(?labelOrigin), '^PARP1$') || regex(str(?labelOrigin), '^PARP6$') || regex(str(?labelOrigin), '^PDGFRA$') || regex(str(?labelOrigin), '^PIK3C*$') || regex(str(?labelOrigin), '^PIK3CA$') || regex(str(?labelOrigin), '^PIK3CB$') || regex(str(?labelOrigin), '^PIK3CD$') || regex(str(?labelOrigin), '^PIM1$') || regex(str(?labelOrigin), '^PIP5K1*$') || regex(str(?labelOrigin), '^PRKC*$') || regex(str(?labelOrigin), '^PRKDC$') || regex(str(?labelOrigin), '^Proteasome$') || regex(str(?labelOrigin), '^PTK2*$') || regex(str(?labelOrigin), '^RAF1$') || regex(str(?labelOrigin), '^RET$') || regex(str(?labelOrigin), '^RRM1$') || regex(str(?labelOrigin), '^SLC16A3$') || regex(str(?labelOrigin), '^SLC16A4$') || regex(str(?labelOrigin), '^SRC$') || regex(str(?labelOrigin), '^SYK$') || regex(str(?labelOrigin), '^TEK$') || regex(str(?labelOrigin), '^Thiamine$') || regex(str(?labelOrigin), '^TIE2$') || regex(str(?labelOrigin), '^TNFA$') || regex(str(?labelOrigin), '^TNFSF10$') || regex(str(?labelOrigin), '^TNKS$') || regex(str(?labelOrigin), '^TOP*$') || regex(str(?labelOrigin), '^TOP1$') || regex(str(?labelOrigin), '^TOP1MT$') || regex(str(?labelOrigin), '^TOP2*$') || regex(str(?labelOrigin), '^TTK$') || regex(str(?labelOrigin), '^TUBB$') || regex(str(?labelOrigin), '^TUBB1$') || regex(str(?labelOrigin), '^TYMS$') || regex(str(?labelOrigin), '^UBA3$') || regex(str(?labelOrigin), '^VEGFR2$') || regex(str(?labelOrigin), '^WEE1$') || regex(str(?labelOrigin), '^WNT*$') || regex(str(?labelOrigin), '^XIAP$') )  .\n" + 
    			"FILTER (?geneProduct != ?targetDown2) . \n"+
    			"FILTER (?targetDown != ?targetDown2) . \n"+
    			"FILTER (?geneProduct != ?targetDown)"+
    			"} ";

    	
    	
/*        		"PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n"+
    			"PREFIX up:<http://purl.uniprot.org/core/>\n"+
    			"PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"+
    			"PREFIX cco: <http://rdf.ebi.ac.uk/terms/chembl#>\n"+
    			"PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>\n"+
//    			"	select distinct ?protein ?ChEMBLTarget \n"+
    			"	select distinct ?drugMec ?ChEMBLTarget \n"+
    			"	  where {\n"+
//    			"      	?protein a up:Protein .\n"+
//    			"      	?protein rdfs:seeAlso ?ChEMBLTarget .\n"+
//    			"       ?ChEMBLTarget up:database <http://purl.uniprot.org/database/ChEMBL>\n"+
//    			"	  SERVICE <https://www.ebi.ac.uk/rdf/services/chembl/sparql>{\n"+
     			"			?drugMec a cco:Mechanism ;\n"+
    			"				cco:hasMolecule ?ChEMBLCompound ;\n"+
    			"				cco:hasTarget ?ChEMBLTarget .\n"+
    			"		} LIMIT 100  #}";*/

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
        ArrayList<String> arSecondDown = new ArrayList<String>();
        
        String firstDown = "";
        String label1 = "";
        String secondDown = "";
        String label2 = "";
        
        String original = "";
//        List<String> ChEMBLTarget;
        
        //loops through the ChemblResults set results until end of file
        //assigns ChEMBL targets to array and writes to output file
        while (firstUpDownResults.hasNext()){
        	QuerySolution firstUpDownSolution = firstUpDownResults.next();
        	if (firstUpDownSolution.get("targetDown") == null){
        		firstDown = "---------------------";
                arFirstDown.add(firstDown);	
                label1 = "--------------------";
                
                /*if (firstUpDownSolution.get("targetDown2") == null) {
                	secondDown = "--------------------";
            		arSecondDown.add(secondDown);
            		label2 = "--------------------";
                }
                else {
                	secondDown = firstUpDownSolution.get("targetDown2").toString();
                	label2 = firstUpDownSolution.get("label3").toString();
                	arSecondDown.add(secondDown);
                }*/
        		
        	}
//            	original = firstUpDownSolution.get("geneProduct").toString();
//            	bw.write(original + "\t" + firstUp + "\t" + firstDown + "\n");
        	else if	(firstUpDownSolution.get("targetDown2") == null){
        		secondDown = "--------------------";
        		arSecondDown.add(secondDown);
        		label2 = "--------------------";
        		/*if (firstUpDownSolution.get("targetDown") == null) {
        			firstDown = "---------------------";
                    arFirstDown.add(firstDown);	
                    label1 = "--------------------";
        		}
        		else {
        			firstDown = firstUpDownSolution.get("targetDown").toString();
                	label1 = firstUpDownSolution.get("label2").toString();
                	arSecondDown.add(secondDown);
        		}*/
        	}
        	else{
        		secondDown = firstUpDownSolution.get("targetDown2").toString();
        		firstDown = firstUpDownSolution.get("targetDown").toString();
        		label1 = firstUpDownSolution.get("label2").toString();
        		label2 = firstUpDownSolution.get("label3").toString();
        		arSecondDown.add(secondDown);
        		arFirstDown.add(firstDown);
//        		original = firstUpDownSolution.get("geneProduct").toString();
//        		bw.write(original + "\t" + firstUp + "\t" + firstDown + "\n");
        		
        	}
        	original = firstUpDownSolution.get("geneProduct").toString();
//        	System.out.println(firstUp);
        	
        	
        	bw.write(original + "\t" + firstDown + "\t" + label1 + "\t" + secondDown + "\t" + label2 + "\n");
        	//bw.write(ChEMBLTarget+"\n");
        	
//        	ChEMBLTarget = ChemblSolution.getResource("ChEMBLTarget").getURI();
//        	toto=solution.get("ChEMBLTarget").toString();
        }
        //check to see what the output of ChEMBLTarget variable is
//        System.out.println(firstUp);
        
		
        System.out.println("Done");
        
        //loop for the elements in the ChEMBL array from the previous query 
        /*for (String UpOne : arFirstUp){
        	for (String DownOne : arFirstDown){
        	//query that takes ChEMBL ID type to a Uniprot ID (that can be used in WPs)
        	String s3 = 
        			"PREFIX wp:      <http://vocabularies.wikipathways.org/wp#>\n"+
        	    			"PREFIX rdfs:    <http://www.w3.org/2000/01/rdf-schema#>\n"+
        	    			"PREFIX dcterms: <http://purl.org/dc/terms/>\n"+

        	    			"SELECT DISTINCT ?geneProduct as ?originURI ?labelOrigin ?targetDown ?sourceUp \n"+
        	    			"WHERE {\n"+
        	    			
        	    			"OPTIONAL\n"+
        	    			"{\n"+
        	    			"  <"+ DownOne +"> dc:identifier ?downOne \n"+  
        	    			"  ?downOne a wp:GeneProduct . \n"+
        	    			"  ?downOne dcterms:isPartOf ?pathway .\n"+
        	    			"  ?pathway a wp:Pathway .\n"+
        	    			"  ?pathway wp:organismName 'Homo sapiens'^^xsd:string .\n"+

        	    			"  ?interactionDown a wp:Interaction .\n"+
        	    			"  ?interactionDown wp:source ?downOne .\n"+
        	    			"  ?interactionDown wp:target ?targetDown .\n"+

        	    			"  ?sourceDown rdfs:label ?labelOrigin .\n"+
        	    			"  ?targetDown rdfs:label ?label1 .\n"+
        	    			"}\n"+

        	    			"OPTIONAL\n"+
        	    			"{\n"+
        	    			"  <"+ UpOne +"> dc:identifier ?upOne \n"+  
        	    			"  ?upOne a wp:GeneProduct . \n"+
        	    			"  ?upOne dcterms:isPartOf ?pathway .\n"+
        	    			"  ?pathway a wp:Pathway .\n"+
        	    			"  ?pathway wp:organismName 'Homo sapiens'^^xsd:string .\n"+

        	    			"  ?interactionUp a wp:Interaction .\n"+
        	    			"  ?interactionUp wp:target ?upOne .\n"+
        	    			"  ?interactionUp wp:source ?sourceUp .\n"+

        	    			"  ?targetUp rdfs:label ?label1 .\n"+
        	    			"  ?sourceUp rdfs:label ?labelOrigin . \n"+
        	    			"}\n"+
        	    				    
        	    			"}";
            
        	//create and execute uniprot query using jena libraries
            Query secondUpDownQuery = QueryFactory.create(s3); //s2 = the query above
//          QueryExecution qExe = QueryExecutionFactory.sparqlService( 
//            "http://sparql.uniprot.org/sparql", query );
            QueryExecution qExeSecondUpDown = QueryExecutionFactory.sparqlService( 
            		"http://sparql.wikipathways.org/", secondUpDownQuery );

            ResultSet secondUpDownResults = qExeSecondUpDown.execSelect();
            
            
            ArrayList<String> arSecondUp = new ArrayList<String>();
            ArrayList<String> arSecondDown = new ArrayList<String>();
            
            String secondUp = "";
            String secondDown = "";
            
            while (secondUpDownResults.hasNext()){
            	QuerySolution secondUpDownSolution = secondUpDownResults.next();
            	secondUp = secondUpDownSolution.get("sourceUp").toString();
            	secondDown = secondUpDownSolution.get("targetDown").toString();
            	System.out.println(secondUp);
            	arSecondUp.add(secondUp);
            	arSecondDown.add(secondDown);
            	bw.append(original + "\t" + firstUp + "\t" + secondUp + "\t" + firstDown + "\t" + secondDown);
            	//bw.append(uniprotTarget+"\t"+targetChEMBL+"\n");
            	//bw.append(targetChEMBL + "\t" + uniprotTarget + "\t" + wpSource +
				//		"\t" + wpTarget + "\t" + wpSecondSource + "\t" + wp2uniprot +
                //    	"\t" + uniprot2ChEMBL + "\n");
            }*/
        
            
            
            
// *********************************            
            
//            ResultSetFormatter.out(System.out, resultsUniprot) ;
            
            //loop for the elements in the uniprot array from the previous query 
            /*for (String Uniprotarget : arUniprot){
            	//System.out.println("dddddddddd\t s"+Uniprotarget+"s");
            	//query for directionality checks using Uniprot IDs 
            	//present in bridge db mappings in WPs RDF
            	String s4 = 
                		"PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n"+
            			"PREFIX wp:	<http://vocabularies.wikipathways.org/wp#>\n"+
            			"PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"+
            			"PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>\n"+
            			"PREFIX dcterms: <http://purl.org/dc/terms/>\n"+
            			"	select distinct ?WpPrimaryIdSource ?target ?wpSource2 ?wp2uniprot \n"+
            			"	  where {\n"+
            			"       ?WpPrimaryIdSource wp:bdbUniprot <" + Uniprotarget + "> .\n"+
            			"       ?WpPrimaryIdSource a wp:DataNode .\n"+
            			
//comment out of query the restriction that keeps the datanodes within the pathway
//            			"       ?WpPrimaryIdSource dcterms:isPartOf ?pathway .\n"+
//            			"       ?pathway a wp:Pathway .\n"+
            			
//            			"       ?interaction dcterms:isPartOf ?pathway .\n"+
            			"       ?interaction a wp:Interaction .\n"+
            			"       ?interaction wp:source ?WpPrimaryIdSource .\n"+
            			"       ?interaction wp:target ?target .\n"+
            			
//						"       ?interaction1 dcterms:isPartOf ?pathway .\n"+
						"       ?interaction1 a wp:Interaction .\n"+
						"       ?interaction1 wp:source ?wpSource2 .\n"+
						"       ?interaction1 wp:target ?target .\n"+
						
						"       ?wpSource2 a wp:DataNode .\n"+
						"       ?wpSource2 wp:bdbUniprot ?wp2uniprot .\n"+

            			
            			"		FILTER(?WpPrimaryIdSource != ?wpSource2)\n"+
            			"		FILTER(?interaction != ?interaction1)\n"+
            			"		}  #}";
                
                Query queryWp = QueryFactory.create(s4); //s2 = the query above
//              QueryExecution qExe = QueryExecutionFactory.sparqlService( 
//                "http://sparql.uniprot.org/sparql", query );
                QueryExecution qExeWp = QueryExecutionFactory.sparqlService( 
                		"http://sparql.wikipathways.org/", queryWp );

                ResultSet resultsWp = qExeWp.execSelect();
                
                //System.out.println(resultsWp.getRowNumber());
                ArrayList<String> arWpSource = new ArrayList<String>();
                ArrayList<String> arWpTarget = new ArrayList<String>();
                ArrayList<String> arWpSecondSource = new ArrayList<String>();
                ArrayList<String> arWp2uniprot = new ArrayList<String>();
                String wpSource = "";
                String wpTarget = "";
                String wpSecondSource = "";
                String wp2uniprot = "";

                
                while (resultsWp.hasNext()){
                	QuerySolution wpSolution = resultsWp.next();
                	wpSource = wpSolution.get("WpPrimaryIdSource").toString();
                	wpTarget = wpSolution.get("target").toString();
                	wpSecondSource = wpSolution.get("wpSource2").toString();
                	wp2uniprot = wpSolution.get("wp2uniprot").toString().replaceAll
                			("identifiers.org", "purl.uniprot.org");
                	
                	//filter results to make sure results for either directionality check 
                	//are not metabolites
                	if (wpSecondSource.matches("(?i).*chebi.*") || 
                			wpTarget.matches("(?i).*chebi.*") ||
                			wpSecondSource.matches("(?i).*wikipathways.*") ||
                			wpTarget.matches("(?i).*wikipathways.*") ||
                			wpTarget.matches("(?i).*hmdb.*") ||
                			wpSecondSource.matches("(?i).*hmdb.*") ||
                			wpTarget.matches("(?i).*pubchem.*") ||
                			wpSecondSource.matches("(?i).*pubchem.*")
                			){}
                	else{
                		System.out.println(wpSource);
                		arWpSource.add(wpSource);
                		arWpTarget.add(wpTarget);
                		arWpSecondSource.add(wpSecondSource);
                		arWp2uniprot.add(wp2uniprot) ;
//                		bw.append(targetChEMBL + "\t" + uniprotTarget + "\t" + wpSource +
//                			"\t" + wpTarget + "\t" + wpSecondSource + "\t" + wp2uniprot +"\n");
                	}
                }
              
                
                for (String SourceUniprot : arWp2uniprot){
                	//query that takes ChEMBL ID type to a Uniprot ID (that can be used in WPs)
                	String s5 = 
                    		"PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n"+
                			"PREFIX up:<http://purl.uniprot.org/core/>\n"+
                			"PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"+
                			"PREFIX cco: <http://rdf.ebi.ac.uk/terms/chembl#>\n"+
                			"PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>\n"+
//                			"	select distinct ?protein \n"+
                			"	select distinct ?ChEMBLTarget2 \n"+
                			"	  where {\n"+
                			"       <" + SourceUniprot + "> a up:Protein ;\n"+
                			"       	 rdfs:seeAlso ?ChEMBLTarget2 .\n"+
                			"       ?ChEMBLTarget2 up:database <http://purl.uniprot.org/database/ChEMBL> .\n"+
//                			"	  SERVICE <https://www.ebi.ac.uk/rdf/services/chembl/sparql>{\n"+
//                 			"			?drugMec a "+ChEMBLTarget+" ;\n"+
//                			"				cco:hasMolecule ?ChEMBLCompound ;\n"+
//                			"				cco:hasTarget ?ChEMBLTarget .\n"+
                			"		}  #}";
                    
                	//create and execute uniprot query using jena libraries
                    Query queryUniprot2 = QueryFactory.create(s5); //s2 = the query above
//                  QueryExecution qExe = QueryExecutionFactory.sparqlService( 
//                    "http://sparql.uniprot.org/sparql", query );
                    QueryExecution qExeUniprot2 = QueryExecutionFactory.sparqlService( 
                    		"http://sparql.uniprot.org/sparql", queryUniprot2 );

                    ResultSet resultsUniprot2 = qExeUniprot2.execSelect();
                    
                    
                    ArrayList<String> arUniprot2 = new ArrayList<String>();
                    String uniprot2ChEMBL = "";
                    
                    while (resultsUniprot2.hasNext()){
                    	QuerySolution uniprotSolution2 = resultsUniprot2.next();
                    	uniprot2ChEMBL = uniprotSolution2.get("ChEMBLTarget2").toString();
                    	//need to use regex replace to change the URI pattern to match the pattern 
                    	//used in WikiPathways in the next query
                    	//uniprot2ChEMBL = uniprotSolution2.get("protein").toString().replaceAll
                    	//		("purl.uniprot.org", "identifiers.org");
//                    	uniprotTarget = uniprotTarget.replaceAll
//                   			("^purl.uniprot.org$", "identifiers.org");
//                    	System.out.println(uniprot2ChEMBL);
                    	arUniprot2.add(uniprot2ChEMBL);
                    	//bw.append(uniprotTarget+"\t"+targetChEMBL+"\n");
						bw.append(targetChEMBL + "\t" + uniprotTarget + "\t" + wpSource +
						"\t" + wpTarget + "\t" + wpSecondSource + "\t" + wp2uniprot +
                    	"\t" + uniprot2ChEMBL + "\n");
                    }*/
                    
                  
        bw.close();}//}
                
//            System.out.print(arUniprot);
            
        
            }  
            

        
        

    
	

