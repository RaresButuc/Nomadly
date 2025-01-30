export default function ChangeLink(isPaginationNeeded, suffLink, link) {
    const currentUrl = new URL(window.location.href);
  
    if (isPaginationNeeded) {
      // Setting Page Number as 1
      currentUrl.searchParams.set("pagenumber", 1);
    }
  
    // Update the sorting parameter
    currentUrl.searchParams.set(suffLink, link);
  
    // Set the updated URL
    window.location.href = currentUrl.toString();
  }