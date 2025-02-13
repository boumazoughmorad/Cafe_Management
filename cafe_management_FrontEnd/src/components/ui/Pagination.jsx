import { useState, useMemo, createContext, useContext } from 'react';
import { Button } from '@/components/ui';
import { ArrowLeft, ArrowRight } from 'lucide-react';
import { cn } from '@/lib/utils/cn';

const PaginationContext = createContext(null);

function usePagination() {
  const context = useContext(PaginationContext);
  if (!context) {
    throw new Error('usePagination must be used within a <Pagination />');
  }
  return context;
}

const Pagination = ({ items = [], itemsPerPage = 9, renderItem, className, ...props }) => {
  const [currentPage, setCurrentPage] = useState(1);
  const totalPages = Math.ceil(items.length / itemsPerPage);

  const currentItems = useMemo(() => {
    return items.slice((currentPage - 1) * itemsPerPage, currentPage * itemsPerPage);
  }, [items, currentPage, itemsPerPage]);

  return (
    <PaginationContext.Provider value={{ currentPage, totalPages, setCurrentPage }}>
      <div className={cn('pagination-container', className)} {...props}>
        {/* Render Items */}
        <div className="flex flex-wrap justify-center gap-4">{currentItems.map(renderItem)}</div>

        {/* Pagination Controls */}
        <PaginationControls />
      </div>
    </PaginationContext.Provider>
  );
};

const PaginationControls = ({ className }) => {
  const { currentPage, totalPages, setCurrentPage } = usePagination();

  return (
    <div className={cn('flex items-center justify-center mt-4 space-x-2', className)}>
      <Button onClick={() => setCurrentPage((prev) => Math.max(prev - 1, 1))} disabled={currentPage === 1}>
        <ArrowLeft className="h-4 w-4 dark:text-black disabled:opacity-20" />
      </Button>

      <span className="px-4 py-2">Page {currentPage} of {totalPages}</span>

      <Button onClick={() => setCurrentPage((prev) => Math.min(prev + 1, totalPages))} disabled={currentPage === totalPages}>
        <ArrowRight className="h-4 w-4 dark:text-black disabled:opacity-20" />
      </Button>
    </div>
  );
};

export  {Pagination};