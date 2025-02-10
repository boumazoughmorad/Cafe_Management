import DataInstructions from "./data/Instructions";

export const getListInstructions=() =>{
  // Gets the Instructions table   
  const Instructions = DataInstructions;

  
  if (!Instructions) {
    console.log('No listings Instructions found');
    return;
  }

  return Instructions;
}