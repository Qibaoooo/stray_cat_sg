const getFileType = (fileName) => {
  const fileNameSplit = fileName.split(".");
  const fileType = fileNameSplit[fileNameSplit.length - 1];
  return fileType;
};

export { getFileType }