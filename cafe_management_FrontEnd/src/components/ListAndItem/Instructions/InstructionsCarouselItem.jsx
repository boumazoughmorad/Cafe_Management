import {
    CarouselItem,
  } from '@/components/ui';
import { memo } from 'react';
const InstructonCarouselItem = ({ instruction }) => {
    return (
      <CarouselItem
        key={instruction.id}
        className='relative pl-0 h-[550px] bg-cover bg-center bg-no-repeat bg-opacity-20 rounded-lg text-center flex items-center justify-center'
        style={{ backgroundImage: `url(${instruction.imageUrl}` }}
      >
        <div className='absolute inset-0 bg-black/50'></div>
        <div className='relative px-[90px] flex flex-col gap-4'>
          <div className='font-bold sm:text-[20px] italic md:text-[40px] text-[20px]'>
            {instruction.title}
          </div>
          <div className='font-bold sm:text-[20px] md:text-[20px] text-[13px]'>
            {instruction.description}
          </div>
        </div>
      </CarouselItem>
    );
  };

  export default memo(InstructonCarouselItem);