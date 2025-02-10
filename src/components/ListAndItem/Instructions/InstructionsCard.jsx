import { getImageUrl } from '@/lib/utils/images';
import {
    Carousel,
    CarouselContent,
    CarouselNext,
    CarouselPrevious,
  } from '@/components/ui';
import {  memo, useCallback, useMemo, useState } from 'react';
import useFetch from '@/hooks/useFetch';
import DataRenderer from '../../DataRenderer';
import InstructonCarouselItem from './InstructionsCarouselItem';
const InstructionsCard = () => {
    const [isHovering, setIsHovering] = useState(false);



    const { data, error, isLoading } = useFetch('/api/instructions');

 
    const memoizedInstructions = useMemo(() => {
    let dataInstructions = data || [] 
      return dataInstructions.map((instruction) => ({
      ...instruction,
      imageUrl: getImageUrl(instruction.image),
    }));
    }, [data]);

    const handleMouseEnter = useCallback(() => setIsHovering(true), []);
    const handleMouseLeave = useCallback(() => setIsHovering(false), []);

  return (
    <div className='w-full'>
    <Carousel
      className='w-full'
      onMouseEnter={handleMouseEnter}
      onMouseLeave={handleMouseLeave}
      opts={{ loop: true }} autoScrollInterval = {3000}
    >
    <CarouselContent className='ml-0 '>
      
       <DataRenderer error={error} isLoading={isLoading}>
       {memoizedInstructions.map((instruction) => (
          <InstructonCarouselItem key={instruction.id} instruction={instruction} />
        ))}
       </DataRenderer>

      </CarouselContent>
      {isHovering && (
        <>
          <CarouselPrevious className='absolute left-4' />
          <CarouselNext className='absolute right-4' />
        </>
      )}
      </Carousel>
    </div>
  )
}

export default memo(InstructionsCard);