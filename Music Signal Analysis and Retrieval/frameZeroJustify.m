function frameMat2 = frameZeroJustify(frameMat, polyOrder);
   [tx, ty] = size(frameMat);
   frame_count = ty;
   frame_size  = tx;
   x_axis = 1:tx;
   x_axis = (x_axis - mean(x_axis)) / std(x_axis);
   for i = 1:frame_count
       this_frame = frameMat(:,i);
       polyy = polyfit(x_axis', this_frame, polyOrder);
       y = polyval(polyy, x_axis');
       for j = 1:frame_size
           this_frame(j) = this_frame(j) - y(j);
       end
       frameMat2(:,i) = this_frame;
   end
end