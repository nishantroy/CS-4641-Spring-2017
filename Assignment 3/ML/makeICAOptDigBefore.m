function [] = makeICAOptDigBefore ()

dimensions = [65];
DigitICA = cell (4,length(dimensions));

data = textread('optdigits.data', '%s', 'delimiter', ',', 'emptyvalue', 0);
data = reshape(data, 65, 5620);
A = str2double(data(1:64, :));
L = str2double(data(65, :));
ALL = A';
pn = ALL;

for i = 1:length(dimensions)
    display (['processing dimension ' num2str(dimensions(i))]);

    tic; 
    
    [icasig, A, W] = fastica (pn, 'lastEig', dimensions(i));
   
    clear W;
    DigitICA{3,i} = toc;
    DigitICA{1,i} = dimensions(i);
    DigitICA{2,i} = A;
    DigitICA{4,i} = max(max (corr(icasig')-eye(size(icasig,1))));

    % plot distribution of kurtosis
    figure;
    hist(kurtosis( icasig'));
    title(['Distribution of kurtosis for ' num2str(dimensions(i)) ' dimensions']);
end

% save DigitICA.mat DigitICA

end