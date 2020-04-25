/*
 * Algorithm based on linear search
 * Worst case O(n)
 * Best case O(1)
 *
 */
const args = process.argv.slice(2);

if (args.length === 0 || args.length === 1) {
  console.log("Call $ node replace.js <string> <replace>");
  process.exit(1);
} else if (args.length > 2) {
  console.log("Only call node replace <string> <replaceString>");
  process.exit(1);
}

const str = args[0];
const replaceStr = args[1];

function replace(baseStr, strToReplace) {
  let resultStr = "";
  const length = baseStr.length;

  for (let i = 0; i <= length - 1; i++) {
    if (baseStr[i] === " ") {
      resultStr += strToReplace;
    } else {
      resultStr += baseStr[i];
    }
  }

  return resultStr;
}

console.log(replace(str, replaceStr));
