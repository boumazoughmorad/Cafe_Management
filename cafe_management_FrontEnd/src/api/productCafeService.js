import dataProduitCafe from "./data/ProduitCafeData";

export const getListProductCafe = (params = {}) => {
  const { search = {} } = params; // Default to an empty object if `search` is undefined
  const searchQuery = typeof search.search === "string" ? search.search : ""; // Ensure `searchQuery` is a string

  console.log("search : ", searchQuery);

  if (!dataProduitCafe) {
    console.log("No listings data Product Cafe found");
    return []; // Return an empty array instead of `undefined`
  }

  // Filter products based on the search query
  let filteredListings = dataProduitCafe;
  if (searchQuery) {
    filteredListings = dataProduitCafe.filter((product) =>
      product.name.toLowerCase().includes(searchQuery.toLowerCase())
    );
  }

  return filteredListings; // Always return an array
};

export const getProductCafeById = (id) =>{

  if (!dataProduitCafe) {
    console.log('No listings data Product Cafe found');
    return;
  }
  
  return dataProduitCafe.find((product) => product.id ===id);
}