input_location <- function(text){
  print(text)
  location <- readline(prompt = "")
  return(location)
}

read_mat_from_file <- function(location){
  Mat <- read.table(location, sep=",", header = T, stringsAsFactors = F)
  return(Mat)
}


folder_loc = input_location("Input location of folder containing the synergy and confidence matrices:")
setwd(folder_loc)
syn_mat <- read.table("synergy_matrix.csv",   sep=",",   blank.lines.skip=F,   header   =   F,  stringsAsFactors = F)
conf_mat <- read.table("confidence_matrix.csv",   sep=",",   blank.lines.skip=F,   header   =   F,  stringsAsFactors = F)
print("Input list location: ")
input_mat <- read.table(readline(prompt=""), sep=",", blank.lines.skip = F, header = F, stringsAsFactors = F)

added <- list()
  
print("Modifying synergy_matrix and confidence_matrix...")
pb_step4 <- txtProgressBar(min = 1, nrow(input_mat), style = 3)
for (i in 1:nrow(input_mat))
{
  
  name_one = toString(input_mat[i,1])
  name_two = paste(unlist(strsplit(toString(input_mat[i,1]), "\\."))[2],".",unlist(strsplit(toString(input_mat[i,1]), "\\."))[1], sep="")
  
  setTxtProgressBar(pb_step4, i)
  for (x in 2:nrow(syn_mat)) 
    if ( (name_one == toString(syn_mat[x,1])) || (name_two == toString(syn_mat[x,1])) )   #if (toString(input_mat[i,1]) == toString(syn_mat[x,1]))
    {
      added <- list(added, toString(input_mat[i,1]))
      for (y in 2:ncol(syn_mat))
      {
        syn_mat[x,y] = 1
        conf_mat[x,y] = 1
      }
    }
}
close(pb_step4)


dir.create("NewlyAddedList")
here  =  file.path("NewlyAddedList",paste0("synergy_matrix.csv"))
write.csv(syn_mat, file = here)

here  =  file.path("NewlyAddedList", paste0("confidence_matrix.csv"))
write.csv(conf_mat, file = here)

#here  =  file.path("NewlyAddedList", paste0("added.csv"))
#write.csv(added, file = here)

# /home/smuraru/Synapse-AstraZeneca/here/Raw_Data/ch2_leaderboard_csv/ProcessedData/Synergistic/Submission/List1Combis
# /home/smuraru/Synapse-AstraZeneca/here/Raw_Data/first2Ints.csv