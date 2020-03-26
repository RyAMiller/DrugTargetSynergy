go_through_step <- function(step){
  print(paste0("Go through ", toString(step), "?", " (y or n)"))
  answer = readline(prompt = "")
  return(answer)
}

input_location <- function(text){
  print(text)
  location <- readline(prompt = "")
  return(location)
}
  
read_mat_from_file <- function(location){
  Mat <- read.table(location, sep=",", header = T, stringsAsFactors = F)
  return(Mat)
}

step_one <- function(){
  setwd(raw_data_loc)
  dir.create("ProcessedData")
  
  print("Step 1. Calculating Loewe Index...")
  pb_step1 <- txtProgressBar(min = 1, nrow(VarSourceMat), style = 3)
  for (r in 1:nrow(VarSourceMat))
  {
    setTxtProgressBar(pb_step1, r)
    ComposedName = paste0(VarSourceMat[r,14], ".", VarSourceMat[r,1])
    DrugA <- VarSourceMat[r,2]
    DrugB <- VarSourceMat[r,3]
    CellLine <- VarSourceMat[r,1]
    HillA <- as.numeric(VarSourceMat[r,7])
    HillB <- as.numeric(VarSourceMat[r,10])
    GammaA <- 1 / HillA
    GammaB <- 1 / HillB
    IC50A <- as.numeric(VarSourceMat[r,6])
    IC50B <- as.numeric(VarSourceMat[r,9])
    
    FileSearch = c(list.files(pattern = ComposedName))
    
    for (k in 1:length(FileSearch))
    {
      Original <-
        read.table(
          FileSearch[k],
          sep = ",",
          blank.lines.skip = F,
          header = F,
          stringsAsFactors = F
        )
      Mat <-
        read.table(
          FileSearch[k],
          sep = ",",
          blank.lines.skip = F,
          nrow = 7,
          header = F
        )
      
      
      for (i in 1:7)
      {
        Mat[1, i] = as.numeric(Mat[1, i])
        Mat[2, i] = as.numeric(Mat[2, i])
      }
      for (j in 1:7)
      {
        Mat[j, 1] = as.numeric(Mat[j, 1])
        Mat[j, 2] = as.numeric(Mat[j, 2])
      }
      
      Nat = Mat
      
      for (i in 3:7)
      {
        Nat[2, i] = (100 - Mat[2, i]) / 100
        if (Nat[2, i] < 0)
          Nat[2, i] <- NA
      }
      
      for (j in 3:7)
      {
        Nat[j, 2] = (100 - Mat[j, 2]) / 100
        if (Nat[j, 2] < 0)
          Nat[j, 2] <- NA
      }
      
      for (i in 3:7)
        for (j in 3:7)
        {
          EmaxA = (Nat[2, i] / (Nat [1, i] ** HillA)) * (Nat[1, i] ** HillA + IC50A **HillA)
          EmaxB = (Nat[j, 2] / (Nat [j, 1] ** HillB)) * (Nat[j, 1] ** HillB + IC50B **HillB)
          Exp1 = Nat[2, i] / (EmaxA - Nat[2, i]) ** GammaA
          Exp2 = Nat[j, 2] / (EmaxB - Nat[j, 2]) ** GammaB
          Object1 = Nat[1, i] / (IC50A * Exp1)
          Object2 = Nat[j, 1] / (IC50B * Exp2)
          Sum <- Object1 + Object2
          if ((is.finite(Sum) == T)&&(Sum>=0))
            Nat[j, i] = Object1 + Object2
          
        }
      filename2 = Original[9, 2]
      filename1 = Original[10, 2]
      filename3 = Original[13, 2]
      setwd("ProcessedData")
      here   =   file.path(paste0(Original[10,2], ".", Original[9,2], ".", Original[13,2], ".", k, ".", "Loewe", ".csv"))
      write.csv(Nat, file = here)
      setwd(raw_data_loc)
      
    }
  }
  close(pb_step1)
  print("Done.")
  
}

step_two <- function() {
  setwd(raw_data_loc)
  setwd("ProcessedData")
  
  dir.create("Synergistic")
  
  find_proc_files = c(list.files(pattern="*.csv"))
  
  print("Out of 25, what is the minimum number of indices that should be calculable?")
  threshold_calc = as.numeric(readline(prompt = ""))
  print("Out of the calculable indices, how many should be synergistic?")
  threshold_syne = as.numeric(readline(prompt = ""))
  
  print("Step 2. Scanning through the processed files...")
  pb_step2 <- txtProgressBar(min = 1, length(find_proc_files), style = 3)
  
  
  s = 1
  B = matrix(nrow=length(find_proc_files), ncol=7)
  B[s,1] = "Drug"
  B[s,2] = "Drug"
  B[s,3] = "Cell Line"
  B[s,4] = "Rep"
  B[s,5] = "Index"
  B[s,6] = "Calculable Indices"
  B[s,7] = "Synergistic Indices"
  for (k in 1:length(find_proc_files))
  {
    setTxtProgressBar(pb_step2, k)
    
    read_combo <- read.table(find_proc_files[k], sep=",", blank.lines.skip=F, header=F)
    count_calc = 0
    count_syn_calc = 0

    for (i in 4:8)
      for (j in 4:8)
        if (is.finite(read_combo[j,i]) == T)
        {
          count_calc = count_calc + 1
          if (as.numeric(as.character(read_combo[j,i])) < 1)
            count_syn_calc = count_syn_calc + 1
        }
    if (count_calc >= threshold_calc)
    {
      if (count_syn_calc >= threshold_syne)
      {
        s = s + 1
        file.copy(find_proc_files[k], "Synergistic", overwrite = F)
        fullname = matrix(unlist(strsplit(basename(find_proc_files[k]), "\\.")), ncol = 6, byrow=T)
        B[s,1] = fullname[1]
        B[s,2] = fullname[2]
        B[s,3] = fullname[3]
        B[s,4] = fullname[4]
        B[s,5] = fullname[5]
        B[s,6] = count_calc
        B[s,7] = count_syn_calc
      }
    }
  }
  
  here = file.path("Synergistic", paste0("SynergyResults.csv"))
  write.csv(B, file = here)
  
  close(pb_step2)
  print("Done.")
}

step_three <- function(){
  setwd(raw_data_loc)
  setwd("ProcessedData/Synergistic")
  
  submission_mat <- matrix(nrow = 371, ncol = 86)
  
  print("Step 3. Initialising the synergy_matrix...")
  pb_step3_i <- txtProgressBar(min = 1, nrow(VarSourceMat)+ncol(VarSourceMat), style = 3)
  
  
  current_row = 2
  for (i in 1:nrow(VarSourceMat))
  {
    setTxtProgressBar(pb_step3_i, i)
    combo_id = VarSourceMat[i,14]
    check_no_dupl = T
    for (k in 1:nrow(submission_mat))
      if (combo_id == toString(submission_mat[k, 1]))
        check_no_dupl = F
    if (check_no_dupl == T)
    {
      submission_mat[current_row, 1] = toString(combo_id)  
      current_row = current_row + 1
    }
  }
  
  current_col = 2
  for (i in 1:nrow(VarSourceMat))
  {
    setTxtProgressBar(pb_step3_i, i + nrow(VarSourceMat))
    cell_line = VarSourceMat[i,1]
    check_no_dupl = T
    for (k in 2:86)
      if (cell_line == toString(submission_mat[1, k]))  
        check_no_dupl = F
    if (check_no_dupl == T)
    {
      submission_mat[1, current_col] = toString(cell_line)
      current_col = current_col + 1
    }
  }
  
  
  
  for (i in 2:nrow(submission_mat))
    for (j in 2:86)
      submission_mat[i, j] = 0
  
  close(pb_step3_i)
  
  syn_res <- read.table("SynergyResults.csv",   sep=",", blank.lines.skip=F, header=T, stringsAsFactors = F)
  
  print("Building the synergy_matrix...")
  pb_step3_s <- txtProgressBar(min = 1, nrow(syn_res), style = 3)
  
  for (i in 2:(nrow(syn_res)))
  {
    setTxtProgressBar(pb_step3_s, i)
    for (k in 2:nrow(submission_mat))
      if (paste0(syn_res[i,2], ".", syn_res[i,3]) == submission_mat[k,1])
        for (p in (2:ncol(submission_mat)))
          if (syn_res[i, 4] == submission_mat[1, p])
            submission_mat[k, p] = 1
  }
    
  dir.create("Submission")
  here = file.path("Submission", paste0("synergy_matrix.csv"))
  write.csv(submission_mat, file = here)
  close(pb_step3_s)
  
  print("Initialising the confidence_matrix")
  pb_step3_ci <- txtProgressBar(min = 1, nrow(syn_res), style = 3)
  
  for (i in 2:nrow(submission_mat))
    for (j in 2:ncol(submission_mat))
      submission_mat[i,j]=0
  
  no_ids = 1
  ids = c(0)
  for (i in 2:nrow(syn_res)) 
  {
    setTxtProgressBar(pb_step3_ci, i)
    check_no_duplicate = T
    id = paste0(syn_res[i,2], ".", syn_res[i,3], ".", syn_res[i,4])
    for (j in 1:length(ids))
      if (ids[j] == id)
        check_no_duplicate = F
    if ((check_no_duplicate == T) && (toString(id) != "NA.NA.NA")) 
    {
      ids[no_ids] = id
      no_ids = no_ids + 1
    }
  }
  close(pb_step3_ci)
  
  setwd(raw_data_loc)
  setwd("ProcessedData")
  
  print("Building the confidence_matrix")
  pb_step3_cm <- txtProgressBar(min = 1, length(ids), style = 3)
  for (i in 1:length(ids))
  {
    setTxtProgressBar(pb_step3_cm, i)
    raw_files = list.files(pattern=toString(ids[i]))
    calc = 0
    conf_score = 0
    for (x in 1:length(raw_files))
    {
      calc_mat <- read.table(raw_files[x],   sep=",",   blank.lines.skip=F,   header=F,   stringsAsFactors   =   F)
      for (row in 4:8)
        for (col in 4:8)
          if (is.finite(as.numeric(calc_mat[row,col])) == T) 
            if (calc_mat[row,col] < 1)
              calc = calc + 1
    }
    conf_score = calc / (length(raw_files)*25)
    name = matrix(unlist(strsplit(basename(ids[i]), "\\.")), ncol = 3, byrow = T)
    for (h in 1:nrow(submission_mat))
    {
      if (paste0(name[1], ".", name[2]) == toString(submission_mat[h,1]))
        for (t in 2:86)
          if (name[3] == submission_mat[1,t])
          {  submission_mat[h,t] = as.character(conf_score)
          # print(name[3])
          # print(submission_mat[h,t])
          # print(conf_score)
          # print(calc)
          # print(length(raw_files))
          # print(calc/(length(raw_files)*25)) 
          }
    }
  }
  close(pb_step3_cm)
  
  
  setwd("Synergistic")
  here = file.path("Submission", paste0("confidence_matrix.csv"))
  write.csv(submission_mat, file = here)
}


VarSourceMat <- read_mat_from_file(input_location("Input location of the file containing monotherapy parameters (eg. Hill, IC50 etc.):"))
# "/home/smuraru/Synapse-AstraZeneca/here/ch2_leaderBoard_monoTherapy.csv"

raw_data_loc = input_location("Input location of raw data (including the subfolder):")
# "/home/smuraru/Synapse-AstraZeneca/here/Raw_Data/ch1_ch2_monoTherapy_csv"
# "/home/smuraru/Synapse-AstraZeneca/here/Raw_Data/ch2_leaderboard_csv"

array_names = c('step_one', 'step_two', 'step_three')
descriptions <- vector(mode="list", length=3)
names(descriptions) <- c('step_one', 'step_two', 'step_three')
descriptions[['step_one']] = 'Step 1 : Calculates Loewe Index for Raw Data Drug Combinations.' 
descriptions[['step_two']] = 'Step 2: Scans through calculations obtained at step 1 and produces a csv file containing those drug combinations for which a minimum number of calculations were possible and a minimum number of those indicate synergy.'
descriptions[['step_three']] = 'Step 3: Builds the synergy_matrix and confidence_matrix.'
for (name in array_names)
{
  print(descriptions[[name]])
  a = go_through_step(name)
  if (a=='y')
    get(name)()
}
print("End of run. In case you ran step 3, please check your csv files for formatting. (Check first row and collumn)")

