import React, { useCallback, useEffect, useMemo, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import DataRenderer from "@/components/DataRenderer";
import ProduitCafeCard from "./ProduitCafeCard";
import { Pagination } from "@/components/ui";
import { getImageUrl } from "@/lib/utils/images";
import { fetchProducts } from "@/state/products/productsSlice";
import Searching from "@/components/Search/Searching";
import useMemoizedData from "@/hooks/useMemoizedData";
import useFetchProducts from "@/hooks/useFetchProdcts";

const ListingProduitCafe = ({ isPageOrder }) => {
  
    // Local state for search and pagination
    const [search, setSearch] = useState("");
    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 16;

  // Fetch options for filtering
  const fetchOptions = useMemo(() => ({ params: { search } }), [search]);

  const { products, orders,error,  status } = useFetchProducts(fetchOptions);



  // Memoized data transformation
  const memoizedData = useMemoizedData(isPageOrder ? orders : products, isPageOrder,isPageOrder ? products : []);


  // Handle search filter changes
  const handleFilters = useCallback((searchValue) => {
    setSearch(searchValue);
  }, []);


  // Paginate memoized data
  const currentmemoizedProduits = useMemo(() => {
    return memoizedData.slice(
      (currentPage - 1) * itemsPerPage,
      currentPage * itemsPerPage
    );
  }, [memoizedData, currentPage, itemsPerPage]);

  // Render paginated products
  const renderProducts = useCallback(
    (product) => (
      <ProduitCafeCard key={product.id} product={product} isPageOrder={isPageOrder} />
    ),
    [isPageOrder]
  );

  return (
    <DataRenderer error={error} isLoading={status === "loading"}>
      <Searching onChange={handleFilters} />
      <div className="flex flex-wrap justify-center md:gap-7 gap-4 mt-10">
        {currentmemoizedProduits.length > 0 ? (
          <Pagination
            items={memoizedData}
            itemsPerPage={itemsPerPage}
            renderItem={renderProducts}
          />
        ) : (
          <p>No products available.</p>
        )}
      </div>
    </DataRenderer>
  );
};

export default React.memo(ListingProduitCafe);